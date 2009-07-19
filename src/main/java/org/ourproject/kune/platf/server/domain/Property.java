package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "property")
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Property implements HasId {

    public enum Type implements IsSerializable {
        STRING, ENUM, BOOL, LIST, HIDDEN
    }

    @Id
    @GeneratedValue
    // (strategy = GenerationType.TABLE)
    private Long id;

    /**
     * Required "symbolic" name of the user preference; displayed to the user
     * during editing if no display_name is defined. Must contain only letters,
     * number and underscores, i.e. the regular expression ^[a-zA-Z0-9_]+$. Must
     * be unique.
     **/
    @Index(name = "propname")
    @Column(unique = true)
    @Pattern(regex = "^[a-zA-Z0-9_]+$", message = "The name can only contain Western characters, numbers, and dashes")
    private String name;

    /**
     * Optional string to display alongside the user preferences in the edit
     * window (in google gadgets must be unique).
     **/
    private String displayName;

    /**
     * Optional string that indicates the data type of this attribute. Can be
     * string, bool, enum, hidden (final a string that is not visible or user
     * editable), or list (final dynamic array generated from user input). The
     * default is string.
     **/
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type datatype;

    /**
     * Optional boolean argument (true or false) indicating whether this user
     * preference is required. The default is false.
     **/
    @Basic
    private boolean required;

    /**
     * Optional string that indicates a user preference's default value.
     **/
    private String defaultValue;

    /**
     * Optional string list that indicates enum valid values.
     **/
    @Basic
    private ArrayList<String> enumValues;

    @NotNull
    @ManyToOne
    private PropertyGroup pgroup;

    @NotNull
    @ManyToOne
    private PropertySubgroup sgroup;

    public Property() {
        this(null, null, Type.STRING, false, null, new ArrayList<String>(), null, null);
    }

    public Property(final Property prop) {
        this.name = prop.getName();
        this.required = prop.getRequired();
        this.datatype = prop.getDatatype();
        this.defaultValue = prop.getDefaultValue();
        this.displayName = prop.getDisplayName();
        this.enumValues = prop.getEnumValues();
        this.pgroup = prop.getPgroup();
        this.sgroup = prop.getSgroup();
    }

    public Property(final String name, final String displayName, final Type datatype, final boolean required,
            final String defaultValue, final ArrayList<String> enumValues, final PropertyGroup pgroup,
            final PropertySubgroup sgroup) {
        this.name = name;
        this.displayName = displayName;
        this.datatype = datatype;
        this.required = required;
        this.defaultValue = defaultValue;
        this.enumValues = enumValues;
        this.pgroup = pgroup;
        this.sgroup = sgroup;
    }

    public Property(final String name, final String displayName, final Type datatype, final boolean required,
            final String defaultValue, final PropertyGroup pgroup, final PropertySubgroup sgroup) {
        this(name, displayName, datatype, required, defaultValue, new ArrayList<String>(), pgroup, sgroup);
    }

    @Finder(query = "SELECT p FROM Property p JOIN p.pgroup g WHERE g = :pgroup")
    public List<Property> find(@Named("pgroup") final PropertyGroup pGroup) {
        return null;
    }

    @Finder(query = "SELECT p.name FROM Property p JOIN p.sgroup g WHERE g = :sgroup")
    public List<String> find(@Named("sgroup") final PropertySubgroup sGroup) {
        return null;
    }

    @Finder(query = "FROM Property p WHERE p.name = :name")
    public Property find(@Named("name") final String name) {
        return null;
    }

    @Finder(query = "FROM Property")
    public List<Property> getAll() {
        return null;
    }

    public Type getDatatype() {
        return datatype;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ArrayList<String> getEnumValues() {
        return enumValues;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PropertyGroup getPgroup() {
        return pgroup;
    }

    public boolean getRequired() {
        return required;
    }

    public PropertySubgroup getSgroup() {
        return sgroup;
    }

    public void setDatatype(final Type datatype) {
        this.datatype = datatype;
    }

    public void setDefaultValue(final String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public void setEnumValues(final ArrayList<String> enumValues) {
        this.enumValues = enumValues;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPgroup(final PropertyGroup pgroup) {
        this.pgroup = pgroup;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public void setSgroup(final PropertySubgroup sgroup) {
        this.sgroup = sgroup;
    }
}
