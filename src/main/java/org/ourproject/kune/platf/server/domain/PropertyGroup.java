package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.NotNull;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "property_group")
public class PropertyGroup implements HasId {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "pgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Property> propertyList;

    @OneToMany(mappedBy = "pgroup")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Properties> propertiesList;

    @NotNull
    @Column(unique = true)
    private String name;

    public PropertyGroup() {
        this(null);
    }

    public PropertyGroup(final String name) {
        this.name = name;
        propertyList = new ArrayList<Property>();
        propertiesList = new ArrayList<Properties>();
    }

    @Finder(query = "FROM PropertyGroup pg WHERE pg.name = :groupname")
    public PropertyGroup find(@Named("groupname") final String groupName) {
        return null;
    }

    @Finder(query = "FROM PropertyGroup")
    public List<PropertyGroup> getAll() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Properties> getPropertiesList() {
        return propertiesList;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPropertiesList(final List<Properties> propertiesList) {
        this.propertiesList = propertiesList;
    }

    public void setPropertyList(final List<Property> propertyList) {
        this.propertyList = propertyList;
    }

}
