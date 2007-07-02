package org.ourproject.kune.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "kune_user")
public class KUser {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String email;
    private String password;

    public KUser(String name, String email) {
	this.name = name;
	this.email = email;
    }
    
    public KUser() {
	this(null, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof KUser)) {
            return false;
        }
        KUser rhs = (KUser) object;
        return new EqualsBuilder().appendSuper(super.equals(object)).append(this.password, rhs.password).append(
        	this.email, rhs.email).append(this.name, rhs.name).append(this.id, rhs.id).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-1968497873, 115967439).appendSuper(super.hashCode()).append(this.password).append(
        	this.email).append(this.name).append(this.id).toHashCode();
    }

    
}
