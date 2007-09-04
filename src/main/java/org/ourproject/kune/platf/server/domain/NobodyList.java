package org.ourproject.kune.platf.server.domain;

public class NobodyList extends GroupList {
    @Override
    public boolean contains(final Group group) {
	return false;
    }
}
