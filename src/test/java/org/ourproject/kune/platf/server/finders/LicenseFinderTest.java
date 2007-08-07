package org.ourproject.kune.platf.server.finders;

import static junit.framework.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.domain.License;

import com.google.inject.Inject;

public class LicenseFinderTest extends PersistenceTest {
    @Inject
    License finder;

    @Before
    public void addData() {
	openTransaction();
	persist(new License("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
		"http://creativecommons.org/licenses/by-nc-nd/3.0/", true, false, false, "", ""));
	persist(new License("gfdl", "GNU Free Documentation License", "", "http://www.gnu.org/copyleft/fdl.html",
		false, true, false, "", ""));
    }

    @Test
    public void findAll() {
	List<License> all = finder.getAll();
	assertEquals(2, all.size());
    }

    @Test
    public void findCC() {
	List<License> cc = finder.getCC();
	assertEquals(1, cc.size());
    }

    @Test
    public void findNotCC() {
	List<License> notCc = finder.getNotCC();
	assertEquals(1, notCc.size());
    }

    @After
    public void close() {
	closeTransaction();
    }

}
