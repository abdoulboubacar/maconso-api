package factory;

import form.DealForm;
import models.Deal;
import models.State;

import java.util.ArrayList;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class DealFactory {

    public static Deal createDeal(DealForm form) {
        Deal deal = new Deal();
        deal.setEndAt(form.getEndAt());
        deal.setStartAt(form.getStartAt());
        deal.setResource(form.getResource());
        deal.setStates(new ArrayList<State>());
        deal.setSupplier(form.getSupplier());
        deal.setUnitPrice(form.getUnitPrice());
        deal.setName(form.getName());
        deal.setPostalCode(form.getPostalCode());
        deal.setSubscriptionPrice(form.getSubscriptionPrice());

        return deal;
    }

    public static void updateDeal(Deal deal, DealForm form) {
        deal.setEndAt(form.getEndAt());
        deal.setStartAt(form.getStartAt());
        deal.setResource(form.getResource());
        deal.setSupplier(form.getSupplier());
        deal.setUnitPrice(form.getUnitPrice());
        deal.setName(form.getName());
        deal.setPostalCode(form.getPostalCode());
        deal.setSubscriptionPrice(form.getSubscriptionPrice());
    }
}
