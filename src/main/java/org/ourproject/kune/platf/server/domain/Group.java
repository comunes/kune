package org.ourproject.kune.platf.server.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "groups")
public class Group implements HasId {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String shortName;

    @Column(unique = true)
    private String longName;

    @Enumerated(EnumType.STRING)
    AdmissionType admissionType;

    @OneToOne
    private ContentDescriptor defaultContent;

    @OneToMany
    private Map<String, Folder> toolRoots;

    private SocialNetwork socialNetwork;

    public Group() {
	this(null, null);
    }

    public Group(final String name, final String shortName) {
	this.shortName = shortName;
	toolRoots = new HashMap<String, Folder>();
    }

    @Finder(query = "from Group")
    public List<Group> getAll() {
	return null;
    }

    @Finder(query = "from Group g where g.shortName = :shortName")
    public Group findByShortName(@Named("shortName")
    final String shortName) {
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

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getLongName() {
	return longName;
    }

    public void setLongName(final String longName) {
	this.longName = longName;
    }

    public ContentDescriptor getDefaultContent() {
	return defaultContent;
    }

    public void setDefaultContent(final ContentDescriptor defaultContent) {
	this.defaultContent = defaultContent;
    }

    public SocialNetwork getSocialNetwork() {
	return socialNetwork;
    }

    public void setSocialNetwork(final SocialNetwork socialNetwork) {
	this.socialNetwork = socialNetwork;
    }

    public Map<String, Folder> getToolRoots() {
	return toolRoots;
    }

    public void setToolRoots(final Map<String, Folder> toolRoots) {
	this.toolRoots = toolRoots;
    }

    public Folder setRootFolder(final String toolName, final Folder root) {
	toolRoots.put(toolName, root);
	return root;
    }

    public Folder getRoot(final String toolName) {
	return toolRoots.get(toolName);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((admissionType == null) ? 0 : admissionType.hashCode());
	result = prime * result + ((defaultContent == null) ? 0 : defaultContent.hashCode());
	result = prime * result + ((longName == null) ? 0 : longName.hashCode());
	result = prime * result + ((shortName == null) ? 0 : shortName.hashCode());
	result = prime * result + ((socialNetwork == null) ? 0 : socialNetwork.hashCode());
	result = prime * result + ((toolRoots == null) ? 0 : toolRoots.hashCode());
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Group other = (Group) obj;
	if (admissionType == null) {
	    if (other.admissionType != null) {
		return false;
	    }
	} else if (!admissionType.equals(other.admissionType)) {
	    return false;
	}
	if (defaultContent == null) {
	    if (other.defaultContent != null) {
		return false;
	    }
	} else if (!defaultContent.equals(other.defaultContent)) {
	    return false;
	}
	if (longName == null) {
	    if (other.longName != null) {
		return false;
	    }
	} else if (!longName.equals(other.longName)) {
	    return false;
	}
	if (shortName == null) {
	    if (other.shortName != null) {
		return false;
	    }
	} else if (!shortName.equals(other.shortName)) {
	    return false;
	}
	if (socialNetwork == null) {
	    if (other.socialNetwork != null) {
		return false;
	    }
	} else if (!socialNetwork.equals(other.socialNetwork)) {
	    return false;
	}
	if (toolRoots == null) {
	    if (other.toolRoots != null) {
		return false;
	    }
	} else if (!toolRoots.equals(other.toolRoots)) {
	    return false;
	}
	return true;
    }

}