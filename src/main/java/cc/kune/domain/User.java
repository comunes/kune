/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package cc.kune.domain;

import java.util.TimeZone;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
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

import cc.kune.core.shared.domain.UserBuddiesVisibility;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.domain.utils.HasId;

@Entity
@Indexed
@Table(name = "kusers")
public class User implements HasId {

    // public static final String PROPS_ID = "userprops";
    // see: http://docs.codehaus.org/display/PICO/Good+Citizen:
    // Never expect or return null
    public static final User UNKNOWN_USER = new User();

    public static boolean isKnownUser(final User user) {
        return !user.equals(UNKNOWN_USER);
    }

    private UserBuddiesVisibility buddiesVisibility;

    @ManyToOne
    @NotNull
    private I18nCountry country;

    @Basic(optional = false)
    private final Long createdOn;

    // @OneToOne(cascade = CascadeType.REMOVE)
    // private final CustomProperties customProperties;

    @Basic
    @Lob
    @Column(nullable = false)
    private byte[] diggest;

    @Column(unique = true, nullable = false)
    @Email
    @Length(min = 1)
    private String email;

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotNull
    private I18nLanguage language;

    @Basic
    private Long lastLogin;

    @Field(index = Index.TOKENIZED, store = Store.NO)
    @Column(nullable = false)
    @Length(min = 3, max = 50)
    private String name;

    @Column(nullable = false)
    @Length(min = 6, max = 40)
    private String password;

    @Basic
    @Lob
    @Column(nullable = false)
    private byte[] salt;

    @Field(index = Index.UN_TOKENIZED, store = Store.NO)
    @Column(unique = true)
    // http://www.hibernate.org/hib_docs/validator/reference/en/html/validator-defineconstraints.html
    @Length(min = 3, max = 15)
    @Pattern(regex = "^[a-z0-9]+$", message = "The name must be between 3 and 15 lowercase characters. It can only contain Western characters, numbers, and dashes")
    private String shortName;

    @NotNull
    private TimeZone timezone;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Group userGroup;

    // @OneToOne
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // private Properties properties;

    public User() {
        this(null, null, null, null, null, null, null, null, null);
    }

    //
    // public User(final String shortName, final String longName, final String
    // email, final String passwd,
    // final I18nLanguage language, final I18nCountry country, final TimeZone
    // timezone) {
    // this(shortName, longName, email, passwd, language, country, timezone,
    // null);
    // }

    public User(final String shortName, final String longName, final String email, final String passwd,
            final byte[] diggets, final byte[] salt, final I18nLanguage language, final I18nCountry country,
            final TimeZone timezone) {
        this.shortName = shortName;
        this.name = longName;
        this.email = email;
        this.password = passwd;
        this.diggest = diggets;
        this.salt = salt;
        this.userGroup = null;
        this.language = language;
        this.country = country;
        this.timezone = timezone;
        // customProperties = new CustomProperties();
        buddiesVisibility = UserBuddiesVisibility.anyone;
        this.createdOn = System.currentTimeMillis();
        this.lastLogin = null;
        // this.properties = properties;
    }

    public UserBuddiesVisibility getBuddiesVisibility() {
        return buddiesVisibility;
    }

    public I18nCountry getCountry() {
        return country;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    // public CustomProperties getCustomProperties() {
    // return customProperties;
    // }

    public byte[] getDiggest() {
        return diggest;
    }

    public String getEmail() {
        return email;
    }

    public boolean getHasLogo() {
        return hasLogo();
    }

    @Override
    public Long getId() {
        return id;
    }

    public I18nLanguage getLanguage() {
        return language;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public String getName() {
        return name;
    }

    // public Properties getProperties() {
    // return properties;
    // }

    public String getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
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

    public void setBuddiesVisibility(final UserBuddiesVisibility buddiesVisibility) {
        this.buddiesVisibility = buddiesVisibility;
    }

    public void setCountry(final I18nCountry country) {
        this.country = country;
    }

    public void setDiggest(final byte[] diggest) {
        this.diggest = diggest;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @Override
    public void setId(final Long id) {
        this.id = id;
    }

    public void setLanguage(final I18nLanguage language) {
        this.language = language;
    }

    public void setLastLogin(final Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setSalt(final byte[] salt) {
        this.salt = salt;
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
