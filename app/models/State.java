package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = State.TABLE)
public class State extends Model {

    public static final String TABLE = "energie_state";

    @Id
    public Long id;

    @Column
    private Long value;

    @Column
    private Long diff;

    @Column
    private Double unitPrice;

    @ManyToOne()
    @JsonIgnore
    private Deal deal;

    @Column
    private Date date;

    public static Finder<Long, Deal> find = new Finder<Long, Deal>(Deal.class);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getDiff() {
        return diff;
    }

    public void setDiff(Long diff) {
        this.diff = diff;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Deal getDeal() {
        return deal;
    }

    public void setDeal(Deal deal) {
        this.deal = deal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
