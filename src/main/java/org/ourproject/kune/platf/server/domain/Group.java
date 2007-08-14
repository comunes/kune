package org.ourproject.kune.platf.server.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
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

    @OneToOne(cascade = CascadeType.ALL)
    private SocialNetwork socialNetwork;

    @OneToMany
    private final Map<String, ToolConfiguration> toolsConfig;

    public Group() {
	this(null, null);
    }

    public Group(final String name, final String shortName) {
	this.shortName = shortName;
	toolsConfig = new HashMap<String, ToolConfiguration>();
	socialNetwork = new SocialNetwork();
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

    public Folder getRoot(final String toolName) {
	return toolsConfig.get(toolName).getRoot();
    }

    public Map<String, ToolConfiguration> getToolsConfig() {
	return toolsConfig;
    }

    public ToolConfiguration getToolConfiguration(final String name) {
	return toolsConfig.get(name);
    }

    public ToolConfiguration setToolConfig(final String name, final ToolConfiguration config) {
	toolsConfig.put(name, config);
	return config;
    }

}