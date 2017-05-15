package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.net.MediaType;
import factory.JwtFactory;
import factory.UserFactory;
import form.LoginForm;
import form.RegisterForm;
import models.User;
import org.mindrot.jbcrypt.BCrypt;
import play.Configuration;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

/**
 * Created by abdoulbou on 04/05/17.
 */
public class SecurityController extends Controller {

    @Inject
    private FormFactory formFactory;

    @Inject
    private Configuration configuration;

    @Inject
    private JwtFactory jwtFactory;

    public Result authenticate() {
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();
        if (loginForm.hasErrors()) {
            return badRequest(loginForm.errorsAsJson());
        }
        session().clear();
        User user = User.findByEmail(loginForm.get().email);
        String cookiName = configuration.getString("play.http.session.cookieName");

        if (user != null && BCrypt.checkpw(loginForm.get().password, user.getPassword())) {
            String token = jwtFactory.createJWT(user.getEmail(), user.getEmail(), user.getEmail());
            user.setAccessToken(token);
            user.save();
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(cookiName, token);
            response().setCookie(Http.Cookie.builder(cookiName, token).withSecure(ctx().request().secure()).build());


            return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
        }

        return badRequest();
    }

    public Result logout() {
        session().clear();
        response().discardCookie(configuration.getString("play.http.session.cookieName"));

        return ok();
    }

    public Result register() {
        Form<RegisterForm> registerForm = formFactory.form(RegisterForm.class).bindFromRequest();
        if (registerForm.hasErrors()) {
            return badRequest(registerForm.errorsAsJson());
        } else {
            String cookiName = configuration.getString("play.http.session.cookieName");
            RegisterForm userForm = registerForm.get();
            UserFactory.createUser(userForm);
            User user = User.findByEmail(userForm.getEmail());

            String token = jwtFactory.createJWT(user.getEmail(), user.getEmail(), user.getEmail());
            ObjectNode authTokenJson = Json.newObject();
            authTokenJson.put(cookiName, token);
            response().setCookie(Http.Cookie.builder(cookiName, token).withSecure(ctx().request().secure()).build());

            user.setAccessToken(token);
            user.save();

            return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
        }
    }
}
