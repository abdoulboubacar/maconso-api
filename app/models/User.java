package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = User.TABLE)
public class User extends Model {

    public static final String TABLE = "energie_user";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Deal> deals;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    @JsonIgnore
    private String password;

    @Column(length = 1000)
    private String accessToken;

    public static Model.Finder<Long, User> find = new Model.Finder<Long, User>(User.class);

    public static User findByEmail(String email) {
        ExpressionList<User> res = find.where().eq("email", email);

        return res.findUnique();
    }

    public static User findByAccessToken(String accessToken) {
        if (accessToken == null) {
            return null;
        }

        try  {
            return find.where().eq("accessToken", accessToken).findUnique();
        }
        catch (Exception e) {
            return null;
        }
    }


    public Long getId() {
        return id;
    }

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

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
