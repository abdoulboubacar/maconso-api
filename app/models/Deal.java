package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = Deal.TABLE)
public class Deal extends Model {

    public static final String TABLE = "energie_deal";

    @javax.persistence.Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;

    @Column
    private String supplier;

    @Column
    private String name;

    @Column
    private Date startAt;

    @Column
    private Date endAt;

    @Column
    private Double unitPrice;

    @Column
    private Integer postalCode;

    @Column
    private Double subscriptionPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deal")
    private List<State> states;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public static Finder<Long, Deal> find = new Finder<Long, Deal>(Deal.class);

    @JsonProperty(value = "lastState")
    public State getLastState() {
        if (getStates().isEmpty()) {
            return null;
        }

        if (getStates().size() == 1) {
            return getStates().get(0);
        }

        return getStates().stream().sorted((state, t1) -> t1.getDate().compareTo(state.getDate())).findFirst().get();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
