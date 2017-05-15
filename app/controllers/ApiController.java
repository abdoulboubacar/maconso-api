package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.MediaType;
import factory.DealFactory;
import factory.StateFactory;
import form.DealForm;
import form.StateForm;
import models.Deal;
import models.Resource;
import models.State;
import models.User;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeField;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.CoefProcessor;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by abdoulbou on 29/12/16.
 */
public class ApiController extends Controller {

    @Inject
    FormFactory formFactory;

    @Inject
    CoefProcessor coefProcessor;

    @Security.Authenticated(Secured.class)
    public Result getDeals(Long userId) {
        return ok(Json.toJson(Secured.getUser().getDeals())).as(MediaType.JSON_UTF_8.toString());
    }

    @Security.Authenticated(Secured.class)
    public Result createDeal() {

        Form<DealForm> form = formFactory.form(DealForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        try {
            DealForm dealForm = form.get();
            Deal deal = DealFactory.createDeal(dealForm);
            User user = Secured.getUser();
            deal.setUser(user);
            deal.save();

            return ok(Json.toJson(deal)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }

    }

    @Security.Authenticated(Secured.class)
    public Result updateDeal(Long dealId) {

        Form<DealForm> form = formFactory.form(DealForm.class).bindFromRequest();
        if (form.hasErrors()) {
            return badRequest(form.errorsAsJson());
        }

        Deal deal = Deal.find.byId(dealId);
        if (deal == null) {
            return badRequest(form.errorsAsJson());
        }

        try {
            DealForm dealForm = form.get();
            DealFactory.updateDeal(deal, dealForm);
            deal.save();

            return ok(Json.toJson(deal)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }

    }

    private JsonNode buildMessage(String message) {
        return Json.toJson(new String[][]{{"message"}, {message}});
    }


    @Security.Authenticated(Secured.class)
    public Result createState(Long dealId) {

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

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();
        if (deal.getLastState() != null && DateUtils.isSameDay(deal.getLastState().getDate(), today)) {
            List<State> states = deal.getStates().stream().sorted((state1, state2) -> state2.getDate().compareTo(state1.getDate())).collect(Collectors.toList());
            if (states.size() >= 2 && stateForm.getValue() < states.get(1).getValue()) {
                return status(NOT_ACCEPTABLE, Json.toJson(new HashMap<String, String>(){{
                    put("message", "STATE_VALUE_NOT_VALID");
                    put("value", String.valueOf(states.get(1).getValue()));
                }})); //la valeur du compteur ne doit pas être inferieure à la précedente saisie
            }
            deal.getLastState().setValue(stateForm.getValue());
            if (states.size() > 2) {
                deal.getLastState().setDiff(stateForm.getValue() - states.get(1).getValue());
            } else {
                deal.getLastState().setDiff(1l);
            }

            deal.getLastState().save();

            return ok(Json.toJson(deal.getLastState())).as(MediaType.JSON_UTF_8.toString());
        }

        if (deal.getLastState() != null && stateForm.getValue() < deal.getLastState().getValue()) {
            return status(NOT_ACCEPTABLE, Json.toJson(new HashMap<String, String>(){{
                put("message", "STATE_VALUE_NOT_VALID");
                put("value", String.valueOf(deal.getLastState().getValue()));
            }})); //la valeur du compteur ne doit pas être inferieure à la précedente saisie
        }

        try {
            state.setDate(today);
            state.setUnitPrice(deal.getUnitPrice());
            state.setSubscriptionPrice(deal.getSubscriptionPrice());
            state.setDeal(deal);
            state.setSupplier(deal.getSupplier());
            coefProcessor.setCoef(state);

            if (deal.getStates() != null && !deal.getStates().isEmpty()) {
                state.setDiff(state.getValue() - deal.getLastState().getValue());
            } else {
                deal.setStates(new ArrayList<State>());
                state.setDiff(1l);
            }

            state.save();

            return ok(Json.toJson(state)).as(MediaType.JSON_UTF_8.toString());
        } catch (Exception e) {
            return badRequest(Json.toJson(e.getMessage()));
        }
    }

    @Security.Authenticated(Secured.class)
    public Result getState(Long dealId) {
        Deal deal = Deal.find.byId(dealId);
        if (deal == null) {
            return notFound("Deal with id " + dealId + " is not found");
        }

        return ok(Json.toJson(deal.getStates())).as(MediaType.JSON_UTF_8.toString());
    }

    @Security.Authenticated(Secured.class)
    public Result getFormattedStates(Long dealId, Integer year, String unit) {
        Deal deal = Deal.find.byId(dealId);
        if (deal == null) {
            return notFound("Deal with id " + dealId + " is not found");
        }
        List<State> states = State.findYearStates(dealId, year);
        Double[] global = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        Double[] conso = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        Double[] subscription = {0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d, 0d};
        DateTime today = new DateTime();
        for (State state : states) {
            DateTime date = new DateTime(state.getDate());
            conso[date.getMonthOfYear() - 1] = conso[date.getMonthOfYear() - 1] + computeStateValue(state, unit);
            if ("EUR".equals(unit)) {
                if (date.getMonthOfYear() < today.getMonthOfYear()) {
                    subscription[date.getMonthOfYear() - 1] = state.getSubscriptionPrice();
                } else if (date.getMonthOfYear() == today.getMonthOfYear()) {
                    subscription[date.getMonthOfYear() - 1] = state.getSubscriptionPrice() * date.getDayOfMonth() / date.dayOfMonth().withMaximumValue().getDayOfMonth();
                }
            }
        }

        for (int i= 0; i< global.length; i++) {
            global[i] = conso[i] + subscription[i];
        }

        Map<String, Double[]> result = new HashMap<String, Double[]>(){{
            put("global", global);
            put("conso", conso);
            put("subscription", subscription);
        }};

        return ok(Json.toJson(result)).as(MediaType.JSON_UTF_8.toString());

    }

    private Double computeStateValue(State state, String unit) {
        Double value = state.getDiff().doubleValue() * state.getCoef();
        if ("EUR".equals(unit)) {
            value = value * state.getUnitPrice();
        }

        return value;
    }

    @Security.Authenticated(Secured.class)
    public Result getResources() {
        return ok(Json.toJson(Resource.find.all())).as(MediaType.JSON_UTF_8.toString());
    }

    @Security.Authenticated(Secured.class)
    public Result getResource(String key) {
        return ok(Json.toJson(Resource.findByKey(key))).as(MediaType.JSON_UTF_8.toString());
    }
}
