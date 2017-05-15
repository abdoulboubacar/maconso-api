package models;

import com.avaje.ebean.*;
import com.avaje.ebean.Query;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = State.TABLE)
public class State extends Model {

    public static final String TABLE = "energie_state";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long id;

    @Column
    private Long value;

    @Column
    private Long coef;

    @Column
    private Long diff;

    @Column
    private Double unitPrice;

    @Column
    private Double subscriptionPrice;

    @Column
    private String supplier;

    @ManyToOne()
    @JsonIgnore
    private Deal deal;

    @Column
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date date;

    public static Finder<Long, State> find = new Finder<Long, State>(State.class);

    public static List<State> findYearStates(Long dealId, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date lowerBoundDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        Date upperBoundDate = calendar.getTime();

        Query<State> res = find.where().eq("deal_id", dealId).between("date", lowerBoundDate, upperBoundDate).orderBy("date ASC");

        return res.findList();
    }

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

    public Long getCoef() {
        return coef;
    }

    public void setCoef(Long coef) {
        this.coef = coef;
    }

    public Double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(Double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}
