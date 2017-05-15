package form;

import play.data.validation.Constraints;

/**
 * Created by abdoulbou on 04/05/17.
 */
public class RegisterForm {
    @Constraints.Required
    @Constraints.Email
    private String email;

    @Constraints.Required
    private String password;

    @Constraints.Required
    private String firstName;

    @Constraints.Required
    private String lastName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
