package form;

import models.Resource;
import models.Supplier;
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

    private Supplier supplier;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date startAt;

    @Formats.DateTime(pattern = "dd/MM/yyyy")
    private Date endAt;

    private Double unitPrice;

    private List<StateForm> states;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
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

}
