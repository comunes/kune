package cc.kune.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import cc.kune.domain.utils.HasId;

import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "property_subgroup")
public class PropertySubgroup implements HasId {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "sgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Property> properties;

    public PropertySubgroup() {
        this.name = null;
        this.properties = new ArrayList<Property>();
    }

    public PropertySubgroup(final String name) {
        this.name = name;
    }

    @Finder(query = "FROM PropertySubgroup")
    public List<PropertySubgroup> getAll() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setProperties(final List<Property> properties) {
        this.properties = properties;
    }
}
