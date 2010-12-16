/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.validator.constraints.Range;

import com.google.inject.name.Named;
import com.wideplay.warp.persist.dao.Finder;

@Entity
@Table(name = "rates", uniqueConstraints = { @UniqueConstraint(columnNames = { "content_id", "rater_id" }) })
public class Rate {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    Content content;

    @ManyToOne
    User rater;

    @Range(min = 0, max = 5)
    Double value;

    @Basic(optional = false)
    private final Long createdOn;

    public Rate() {
        this(null, null, null);
    }

    public Rate(final User rater, final Content content, final Double value) {
        this.rater = rater;
        this.content = content;
        this.value = value;
        this.createdOn = System.currentTimeMillis();
    }

    @Finder(query = "SELECT AVG(r.value) FROM Rate r WHERE r.content = :content")
    public Double calculateRate(@Named("content") final Content content) {
        return null;
    }

    @Finder(query = "SELECT count(*) FROM Rate r WHERE r.content = :content")
    public Long calculateRateNumberOfUsers(@Named("content") final Content content) {
        return null;
    }

    @Finder(query = "SELECT r FROM Rate r WHERE r.rater = :user AND r.content = :content")
    public Rate find(@Named("user") final User user, @Named("content") final Content content) {
        return null;
    }

    public Content getContent() {
        return content;
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public Long getId() {
        return id;
    }

    public User getRater() {
        return rater;
    }

    public Double getValue() {
        return value;
    }

    public void setContent(final Content content) {
        this.content = content;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setRater(final User rater) {
        this.rater = rater;
    }

    public void setValue(final Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Rate[" + getRater() + " to " + content.getStateTokenEncoded() + "rated: " + getValue() + "]";
    }
}
