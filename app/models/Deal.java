package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.*;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = Deal.TABLE)
public class Deal extends Model {

    public static final String TABLE = "energie_deal";

    @Id
    public Long id;

    @Column
    @Enumerated(EnumType.STRING)
    @Constraints.Required
    private Resource resource;

    @Column
    @Enumerated(EnumType.STRING)
    private Supplier supplier;

    @Column
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date startAt;

    @Column
    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date endAt;

    @Column
    private Double unitPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deal")
    private List<State> states;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName="id")
    @Constraints.Required
    @JsonIgnore
    private User user;

    public static Finder<Long, Deal> find = new Finder<Long, Deal>(Deal.class);

    public State getState(Date date) {
        return getStates().stream().filter(state -> state.getDate().equals(date)).findFirst().get();
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
}
