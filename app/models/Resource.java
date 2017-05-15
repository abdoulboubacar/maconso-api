package models;

import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Model;

import javax.persistence.*;

/**
 * Created by abdoulbou on 29/12/16.
 */
@Entity
@Table(name = Resource.TABLE)
public class Resource {
    public static final String TABLE = "energie_resource";

    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id;

    @Column(unique = true)
    private String key;

    @Column
    private String name;

    @Column
    private String unit;

    public static Model.Finder<Long, Resource> find = new Model.Finder<Long, Resource>(Resource.class);

    public static Resource findByKey(String key) {
        ExpressionList<Resource> res = find.where().eq("key", key);

        return res.findUnique();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }
}

