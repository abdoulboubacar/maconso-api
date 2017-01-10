package factory;

import form.UserForm;
import models.Deal;
import models.User;

import java.util.ArrayList;

/**
 * Created by abdoulbou on 10/01/17.
 */
public class UserFactory {

    public static User createUser(UserForm form) {
        User user = new User();
        user.setDeals(new ArrayList<Deal>());
        user.setEmail(form.getEmail());
        user.setName(form.getName());

        user.save();
        return user;
    }

    public static  User getUser(String email) {
        return User.findByEmail(email);
    }

    public static User updateUser() {
        //TODO
        return null;
    }

}
