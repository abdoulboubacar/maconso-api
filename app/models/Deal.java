package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Id
    public Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Resource resource;

    @Column
    @Enumerated(EnumType.STRING)
    private Supplier supplier;

    @Column
    private Date startAt;

    @Column
    private Date endAt;

    @Column
    private Double unitPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deal")
    private List<State> states;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    public static Finder<Long, Deal> find = new Finder<Long, Deal>(Deal.class);

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
