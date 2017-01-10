package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

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

    public static Finder<Long, User> find = new Finder<Long, User>(User.class);

    public static User findByEmail(String email) {
        ExpressionList<User> res = find.where().eq("email", email);

        return res.findUnique();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User aux = (User) obj;

        return email.equals(aux.email);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (int) (prime * result + ((id == null) ? 0 : id));
        result = prime * result + ((email == null) ? 0 : email.hashCode());

        return result;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
