package controllers;

import com.google.common.net.MediaType;
import com.google.inject.Inject;
import factory.DealFactory;
import factory.StateFactory;
import factory.UserFactory;
import form.DealForm;
import form.StateForm;
import form.UserForm;
import io.swagger.annotations.*;
import models.Deal;
import models.State;
import models.User;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.swagger;

import java.text.SimpleDateFormat;
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

    public Result swagger() {
        return ok(swagger.render());
    }

    @ApiOperation(value = "Create a user", response = UserForm.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "User's name", required = true, dataType = "string", paramType = "form", defaultValue = "Razak"),
            @ApiImplicitParam(name = "email", value = "User's email", required = true, dataType = "string", paramType = "form", defaultValue = "test@gmail.com"),
    })
    public Result createUser() {
                Form<UserForm> form = formFactory.form(UserForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        try {
            UserForm userForm = form.get();
            User user = UserFactory.createUser(userForm);
            return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }

    }

    @ApiOperation(value = "Get a user", response = UserForm.class)
    public Result getUser(@ApiParam(value = "User email", defaultValue = "test@gmail.com") String email) {
        
        User user = User.findByEmail(email);
        if (user == null) {
            return notFoundResult("not fount user " + email);
        }

        return ok(Json.toJson(user)).as(MediaType.JSON_UTF_8.toString());
    }

    @ApiOperation(value = "Get a deal", response = DealForm.class)
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "resource", value = "Resource type", required = true, dataType = "string", paramType = "form", defaultValue = "GAS"),
            @ApiImplicitParam(name = "unitPrice", value = "Unit price", required = true, dataType = "double", paramType = "form", defaultValue = "0.0675"),
            @ApiImplicitParam(name = "supplier", value = "Supplier", required = false, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "startAt", value = "Start date", required = false, dataType = "date", paramType = "form", example = "dd/MM/yyyy"),
            @ApiImplicitParam(name = "endAt", value = "End date", required = false, dataType = "date", paramType = "form", example = "dd/MM/yyyy"),
    })
    public Result createDeal(@ApiParam(value = "User's email", defaultValue = "test@gmail.com", required = true) String email) {
        
        Form<DealForm> form = formFactory.form(DealForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        try {
            DealForm dealForm = form.get();
            Deal deal = DealFactory.createDeal(dealForm);
            User user = User.findByEmail(email);
            deal.setUser(user);
            deal.save();

            return ok(Json.toJson(deal)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }

    }

    private Result notFoundResult(String message) {
        return notFound(Json.toJson(new String[][]{{"message"}, {message}}));
    }

    @ApiOperation(value = "Add state", response = StateForm.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "value", value = "Value", required = true, dataType = "double", paramType = "form"),
    })
    public Result createState(@ApiParam(value = "Associated deal ID") Long dealId) {
        
        Form<StateForm> form = formFactory.form(StateForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }
        StateForm stateForm = form.get();
        State state = StateFactory.createState(stateForm);

        Deal deal = Deal.find.byId(dealId);
        if (deal == null) {
            return notFound("Deal with id " + dealId + " is not found");
        }

        try {
            Calendar calendar = Calendar.getInstance();
            Date today = calendar.getTime();
            state.setDate(today);
            state.setUnitPrice(deal.getUnitPrice());

            if (deal.getStates() != null && !deal.getStates().isEmpty()) {
                state.setDiff(state.getValue() - deal.getLastState().getValue());
            } else {
                deal.setStates(new ArrayList<State>());
                state.setDiff(0l);
            }

            state.setDeal(deal);
            state.save();

            return ok(Json.toJson(state)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }
    }
}
