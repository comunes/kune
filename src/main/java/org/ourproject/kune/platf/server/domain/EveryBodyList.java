package org.ourproject.kune.platf.server.domain;

import javax.persistence.Entity;

@Entity
public class EveryBodyList extends GroupList {
    @Override
    public boolean contains(final Group group) {
	return true;
    }
}
