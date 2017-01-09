package controllers;

import com.google.common.net.MediaType;
import com.google.inject.Inject;
import io.swagger.annotations.*;
import models.*;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.swagger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Api(value = "/energie", description = "Gestion de ma consommation énergétique")
public class ApiController extends Controller {

    @Inject
    FormFactory formFactory;

    private void allowCrosHeaders() {
        response().setHeader("Access-Control-Allow-Origin", "*");
        response().setHeader("Allow", "*");
        response().setHeader("Access-Control-Allow-Methods", "POST, PATCH, GET, PUT, DELETE, OPTIONS");
        response().setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Referer, User-Agent");
    }

    public Result swagger() {
        return ok(swagger.render());
    }

    @ApiOperation(value = "Create a user", response = User.class)
    public Result createUser() {
        allowCrosHeaders();
        Form<User> form = formFactory.form(User.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        User user = form.get();
        user.save();

        return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
    }

    @ApiOperation(value = "Get a user", response = User.class)
    @ApiParam(name = "email")
    public Result getUser(String email) {
        allowCrosHeaders();

        User user = User.findByEmail(email);
        if (user == null) {
            return notFoundResult("not fount user " + email);
        }

        return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
    }

    @ApiOperation(value = "Get a deal", response = Deal.class)
    public Result createDeal() {
        allowCrosHeaders();

        Form<Deal> form = formFactory.form(Deal.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        Deal deal = form.get();
        deal.save();
        return ok(Json.toJson(deal)).as(MediaType.JSON_UTF_8.toString());
    }

    @ApiOperation(value = "Add state", response = State.class)
    public Result createState() {
        allowCrosHeaders();

        Form<State> form = formFactory.form(State.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        State state = form.get();
        if (state.getDeal().getStates() == null || state.getDeal().getStates().isEmpty()) {
            state.getDeal().setStates(new ArrayList<State>());
            state.setDiff(0l);
        } else {
            calendar.add(Calendar.DATE, -1);
            state.setDiff(state.getValue() - state.getDeal().getState(calendar.getTime()).getValue());
        }
        state.setUnitPrice(state.getDeal().getUnitPrice());
        state.setDate(today);
        state.getDeal().getStates().add(state);

        state.save();

        return ok(Json.toJson(state)).as(MediaType.JSON_UTF_8.toString());
    }

    private Result notFoundResult(String message) {
        return notFound(Json.toJson(new String[][]{{"message"}, {message}}));
    }
}
