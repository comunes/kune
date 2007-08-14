package org.ourproject.kune.platf.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "group_list")
public class GroupList {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany()
    List<Group> list;

    public GroupList() {
	this(new ArrayList<Group>());
    }

    public GroupList(final List<Group> list) {
	this.list = list;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public List<Group> getList() {
	return list;
    }

    public void setList(final List<Group> list) {
	this.list = list;
    }

    public void add(final Group group) {
	this.add(group);
    }

}
