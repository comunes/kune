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

}