package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "users")
public class User implements HasId {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private Group userGroup;

    public User(final String email, final String password) {
	this.email = email;
	this.password = password;
    }

    public User() {
	this(null, null);
    }

    @Finder(query = "from User")
    public List<User> getAll() {
	return null;
    }

    @Finder(query = "from User where email = :email")
    public User getByEmail(@Named("email")
    final String email) {
	return null;
    }

    public Long getId() {
	return id;
    }

    public void setId(final Long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(final String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(final String password) {
	this.password = password;
    }

    public Group getUserGroup() {
	return userGroup;
    }

    public void setUserGroup(final Group userGroup) {
	this.userGroup = userGroup;
    }

}
