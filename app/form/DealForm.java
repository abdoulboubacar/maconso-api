package form;

import models.Resource;
import play.data.format.Formats;
import play.data.validation.Constraints;

import java.util.Date;
import java.util.List;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class DealForm {

    @Constraints.Required
    private Resource resource;

    @Constraints.Required
    private String supplier;

    @Constraints.Required
    private Integer postalCode;

    private String name;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date startAt;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date endAt;

    private Double unitPrice;

    private Double subscriptionPrice;

    private List<StateForm> states;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<StateForm> getStates() {
        return states;
    }

    public void setStates(List<StateForm> states) {
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public Double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }
}
