package cc.kune.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cc.kune.domain.Property.Type;
import cc.kune.domain.utils.HasId;

import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "property_setted")
public class PropertySetted implements HasId {

    @Id
    @GeneratedValue
    private Long id;

    private String value;

    @NotNull
    @ManyToOne
    private final Property property;

    public PropertySetted() {
        this(null, null);
    }

    public PropertySetted(final Property property, final String value) {
        this.value = value;
        this.property = property;
    }

    @Finder(query = "FROM PropertySetted")
    public List<PropertySetted> getAll() {
        return null;
    }

    public Type getDatatype() {
        return property.getDatatype();
    }

    public String getDefaultValue() {
        return property.getDefaultValue();
    }

    public String getDisplayName() {
        return property.getDisplayName();
    }

    public ArrayList<String> getEnumValues() {
        return property.getEnumValues();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return property.getName();
    }

    public PropertyGroup getPgroup() {
        return property.getPgroup();
    }

    public Property getProperty() {
        return property;
    }

    public boolean getRequired() {
        return property.getRequired();
    }

    public PropertySubgroup getSgroup() {
        return property.getSgroup();
    }

    public String getValue() {
        return value;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
