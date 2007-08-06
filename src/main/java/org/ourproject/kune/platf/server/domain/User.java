package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "users")
public class User {
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

    @Finder(query = "from User")
    public List<User> getAll() {
	return null;
    }

    @Finder(query = "from User where email = :email")
    public User getByEmail(@Named("email")
    final String email) {
	return null;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(final Object object) {
	if (!(object instanceof User)) {
	    return false;
	}
	User rhs = (User) object;
	return new EqualsBuilder().appendSuper(super.equals(object)).append(this.password, rhs.password).append(
		this.email, rhs.email).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
	return new HashCodeBuilder(1063862927, -516313271).appendSuper(super.hashCode()).append(this.password).append(
		this.email).toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
	return new ToStringBuilder(this).append("email", this.email).append("password", this.password).toString();
    }

}
