package org.ourproject.kune.platf.server.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "revisions")
public class Revision {
    @Id
    @GeneratedValue
    private Long id;
    private User editor;

    @Basic(optional = false)
    private Long modifiedOn;

    private Text content;

    @Version
    private int version;

    @OneToOne(fetch = FetchType.LAZY)
    private Revision previous;

}
