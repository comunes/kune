/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.server.domain;

import java.util.List;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.hibernate.validator.Email;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Pattern;
import org.ourproject.kune.platf.client.dto.StateToken;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Indexed
@Table(name = "kusers")
public class User implements HasId {

    // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
    // Never expect or return null
    public static final User UNKNOWN_USER = new User();

    public static boolean isKnownUser(final User user) {
        return user != UNKNOWN_USER;
    }

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    @Email
    @Length(min = 1)
    private String email;

    @Column(nullable = false)
    @Length(min = 6, max = 40)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Group userGroup;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(nullable = false)
    @Length(min = 3, max = 50)
    private String name;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(unique = true)
    // http://www.hibernate.org/hib_docs/validator/reference/en/html/validator-defineconstraints.html
    @Length(min = 3, max = 15)
    @Pattern(regex = "^[a-z0-9_\\-]+$", message = "The name must be between 3 and 15 lowercase characters. It can only contain Western characters, numbers, and dashes")
    private String shortName;

    @ManyToOne
    @NotNull
    private I18nLanguage language;

    @ManyToOne
    @NotNull
    private I18nCountry country;

    @NotNull
    private TimeZone timezone;

    @OneToOne(cascade = CascadeType.ALL)
    private final CustomProperties customProperties;

    private UserBuddiesVisibility buddiesVisibility;

    public User() {
        this(null, null, null, null, null, null, null);
    }

    public User(final String shortName, final String longName, final String email, final String passwd,
            final I18nLanguage language, final I18nCountry country, final TimeZone timezone) {
        this.shortName = shortName;
        this.name = longName;
        this.email = email;
        this.password = passwd;
        this.userGroup = null;
        this.language = language;
        this.country = country;
        this.timezone = timezone;
        customProperties = new CustomProperties();
        buddiesVisibility = UserBuddiesVisibility.anyone;
    }

    @Finder(query = "from User")
    public List<User> getAll() {
        return null;
    }

    public UserBuddiesVisibility getBuddiesVisibility() {
        return buddiesVisibility;
    }

    @Finder(query = "from User where email = :email")
    public User getByEmail(@Named("email") final String email) {
        return null;
    }

    @Finder(query = "from User where shortName = :shortName")
    public User getByShortName(@Named("shortName") final String shortName) {
        return null;
    }

    public I18nCountry getCountry() {
        return country;
    }

    public CustomProperties getCustomProperties() {
        return customProperties;
    }

    public String getEmail() {
        return email;
    }

    public boolean getHasLogo() {
        return hasLogo();
    }

    public Long getId() {
        return id;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getShortName() {
        return shortName;
    }

    @Transient
    public StateToken getStateToken() {
        return userGroup.getStateToken();
    }

    public TimeZone getTimezone() {
        return timezone;
    }

    public Group getUserGroup() {
        return userGroup;
    }

    @Transient
    public boolean hasLogo() {
        return getUserGroup().hasLogo();
    }

    public void setBuddiesVisibility(UserBuddiesVisibility buddiesVisibility) {
        this.buddiesVisibility = buddiesVisibility;
    }

    public void setCountry(final I18nCountry country) {
        this.country = country;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
        // http://www.dynamic.net.au/christos/crypt/
        // Use UnixCrypt (jetty)
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public void setTimezone(final TimeZone timezone) {
        this.timezone = timezone;
    }

    public void setUserGroup(final Group userGroup) {
        this.userGroup = userGroup;
    }

    @Override
    public String toString() {
        return "User[" + shortName + "]";
    }
}
