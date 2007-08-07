package org.ourproject.kune.platf.server.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.client.dto.GroupDTO;
import org.ourproject.kune.platf.server.TestHelper;
import org.ourproject.kune.platf.server.domain.Group;

import com.google.inject.Inject;

public class MapperTest {
    @Inject
    Mapper mapper;

    @Before
    public void inject() {
	TestHelper.inject(this);
    }

    @Test
    public void testGroupMapping() {
	Group group = new Group("name", "shortName");
	GroupDTO dto = mapper.map(group, GroupDTO.class);
	assertEquals(group.getName(), dto.getName());
	assertEquals(group.getShortName(), dto.getShortName());
    }
}
