package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "groups")
public class Group extends Container {
    private String shortName;
    @Enumerated(EnumType.STRING)
    AdmissionType admissionType;

    public Group() {
	this(null, null);
    }

    public Group(final String name, final String shortName) {
	super(name);
	this.shortName = shortName;
    }

    @Finder(query = "from Group")
    public List<Group> getAll() {
	return null;
    }

    public String getShortName() {
	return shortName;
    }

    public void setShortName(final String shortName) {
	this.shortName = shortName;
    }

    public AdmissionType getAdmissionType() {
	return admissionType;
    }

    public void setAdmissionType(final AdmissionType admissionType) {
	this.admissionType = admissionType;
    }

}
