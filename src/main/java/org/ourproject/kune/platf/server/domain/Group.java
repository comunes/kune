package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
public class Group extends Container {
    private String shortName;
    @Enumerated(EnumType.STRING)
    AdmissionType admissionType;

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
