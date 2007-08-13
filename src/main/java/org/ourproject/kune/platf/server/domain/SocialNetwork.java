package org.ourproject.kune.platf.server.domain;

import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

@Embeddable
public class SocialNetwork {

    @OneToMany
    List<Group> admins;

    @OneToMany
    List<Group> collaborators;

    @OneToMany
    List<Group> viewer;

    @OneToMany
    List<Group> pendingCollaborators;

}
