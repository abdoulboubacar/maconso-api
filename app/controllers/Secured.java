package controllers;

import factory.JwtFactory;
import models.User;
import org.apache.commons.lang3.StringUtils;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;

import javax.inject.Inject;

/**
 * Created by abdoulbou on 05/05/17.
 */
public class Secured extends Security.Authenticator {
    @Inject
    private JwtFactory jwtFactory;

    @Override
    public String getUsername(Http.Context ctx) {
        String[] authTokenHeaderValues = ctx.request().headers().get("Authorization");
        if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1) && (StringUtils.isNotBlank(authTokenHeaderValues[0]))) {
            User user = User.findByAccessToken(authTokenHeaderValues[0]);
            if (user != null && jwtFactory.isValidJWT(authTokenHeaderValues[0], user.getEmail())) {
                ctx.args.put("user", user);

                return user.getEmail();
            }
        }

        return null;
    }

    @Override
    public Result onUnauthorized(Http.Context ctx) {
        return unauthorized();
    }

    public static User getUser() {
        return (User)Http.Context.current().args.get("user");
    }
}
