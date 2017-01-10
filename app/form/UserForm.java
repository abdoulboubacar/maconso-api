package form;

import io.swagger.annotations.ApiParam;
import play.data.validation.Constraints;

import java.util.List;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class UserForm {
    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.Required
    private String name;

    private List<DealForm> deals;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DealForm> getDeals() {
        return deals;
    }

    public void setDeals(List<DealForm> deals) {
        this.deals = deals;
    }
}
