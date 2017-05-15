package form;

import play.data.validation.Constraints;

/**
 * Created by abdoulbou on 04/05/17.
 */
public class LoginForm {
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.Required
    public String password;
}