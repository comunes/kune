package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "containers")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Container implements HasId {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Alias> aliases;

    @OneToOne
    private Group owner;

    public Container(final String name) {
	this.name = name;
    }

    public Container() {
	this(null);
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(final String name) {
	this.name = name;
    }

    public List<Alias> getAliases() {
	return aliases;
    }

    public void setAliases(final List<Alias> aliases) {
	this.aliases = aliases;
    }

    public Group getOwner() {
	return owner;
    }

    public void setOwner(final Group owner) {
	this.owner = owner;
    }

}
