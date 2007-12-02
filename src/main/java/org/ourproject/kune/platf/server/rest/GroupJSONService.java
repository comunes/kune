package org.ourproject.kune.platf.server.rest;

import java.util.List;

import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;
import org.ourproject.rack.filters.rest.REST;

import com.google.inject.Inject;

public class GroupJSONService {
	private final GroupManager manager;

	@Inject
	public GroupJSONService(GroupManager manager) {
		this.manager = manager;
	}

	@REST(params={"query"})
	List<Group> search(String search) {
		return manager.search(search);
	}

	@REST(params={"query", "fisrt", "max"})
	List<Group> search(String search, Integer firstResult, Integer maxResults) {
		return manager.search(search, firstResult, maxResults);
	}
}