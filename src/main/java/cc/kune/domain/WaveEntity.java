/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import cc.kune.domain.utils.DataFieldBridge;

@Entity
@IdClass(WaveRefKey.class)
@Indexed
@Table(name = "waves")
public class WaveEntity implements Serializable, Comparator<WaveEntity> {

  private static final long serialVersionUID = 8890747607044939769L;

  @Basic
  private Long creationTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id")
  private ParticipantEntity creator;
  @Id
  private String domain;
  @Basic
  private Long lastModifiedTime;

  /*
   * Using fetch = FetchType.EAGER to avoid this on startup:
   * http://stackoverflow.com/questions/20162077/hibernate-org-hibernate-lazyinitializationexception-failed-to-lazily-initialize
   */
  @ManyToMany(mappedBy = "waves", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
  private Set<ParticipantEntity> participants;

  // http://www.hibernate.org/112.html
  /** The body. */
  @Lob
  @Column(length = 2147483647)
  @Field(index = Index.YES, store = Store.NO)
  @FieldBridge(impl = DataFieldBridge.class)
  private char[] rendered;

  @Id
  private String waveId;

  @Id
  private String waveletId;

  public WaveEntity() {
  }

  public WaveEntity(final String domain, final String waveId, final String waveletId,
      final Long lastModifiedTime, final ParticipantEntity creator, final Long creationTime) {
    this.domain = domain;
    this.waveId = waveId;
    this.waveletId = waveletId;
    this.lastModifiedTime = lastModifiedTime;
    this.creator = creator;
    this.creationTime = creationTime;
    this.participants = new HashSet<ParticipantEntity>();
  }

  public void add(final ParticipantEntity participant) {
    participants.size();
    participants.add(participant);
    participant.add(this);
  }

  @Override
  public int compare(final WaveEntity one, final WaveEntity two) {
    return one.getLastModifiedTime().compareTo(two.getLastModifiedTime());
  }

  public Long getCreationTime() {
    return creationTime;
  }

  public ParticipantEntity getCreator() {
    return creator;
  }

  public String getDomain() {
    return domain;
  }

  public Long getLastModifiedTime() {
    return lastModifiedTime;
  }

  public Set<ParticipantEntity> getParticipants() {
    return participants;
  }

  public char[] getRendered() {
    return rendered;
  }

  public String getWaveId() {
    return waveId;
  }

  public String getWaveletId() {
    return waveletId;
  }

  public void remove(final ParticipantEntity participant) {
    participants.size();
    participants.remove(participant);
    participant.remove(this);
  }

  public void setCreationTime(final Long creationTime) {
    this.creationTime = creationTime;
  }

  public void setCreator(final ParticipantEntity creator) {
    this.creator = creator;
  }

  public void setDomain(final String domain) {
    this.domain = domain;
  }

  public void setLastModifiedTime(final Long lastModifiedTime) {
    this.lastModifiedTime = lastModifiedTime;
  }

  public void setParticipants(final Set<ParticipantEntity> participants) {
    this.participants = participants;
  }

  public void setRendered(final char[] rendered) {
    this.rendered = rendered;
  }

  public void setRendered(final String rendered) {
    this.rendered = rendered.toCharArray();
  }

  public void setWaveId(final String waveId) {
    this.waveId = waveId;
  }

  public void setWaveletId(final String waveletId) {
    this.waveletId = waveletId;
  }

}
