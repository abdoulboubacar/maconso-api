package factory;

import form.RegisterForm;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class UserFactory {

    public static void createUser(RegisterForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.save();
    }

    public static  User getUser(String email) {
        return User.findByEmail(email);
    }

    public static User updateUser() {
        //TODO
        return null;
    }

}
