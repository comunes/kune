package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.model.AccessRights;
import org.ourproject.kune.platf.server.model.Content;
import org.ourproject.kune.workspace.client.dto.ContentDTO;

import com.google.inject.Inject;

public class MapperTest {
    @Inject
    Mapper mapper;

    @Before
    public void inject() {
	TestHelper.inject(this);
    }

    @Test
    public void testContentMapping() {
	Content c = new Content();
	c.setAccessRights(new AccessRights(true, true, true));

	ContentDTO dto = mapper.map(c, ContentDTO.class);
	assertEquals(c.getAccessRights().isAdministrable(), dto.getAccessRights().isAdministrable);
    }

    @Test
    public void testGroupMapping() {
	Group group = new Group("shortName", "name");
	GroupDTO dto = mapper.map(group, GroupDTO.class);
	assertEquals(group.getLongName(), dto.getLongName());
	assertEquals(group.getShortName(), dto.getShortName());
    }
}
