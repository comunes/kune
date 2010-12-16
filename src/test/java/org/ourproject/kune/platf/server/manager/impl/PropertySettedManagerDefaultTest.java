package org.ourproject.kune.platf.server.manager.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ourproject.kune.platf.server.PersistenceTest;
import org.ourproject.kune.platf.server.manager.PropertiesManager;
import org.ourproject.kune.platf.server.manager.PropertyGroupManager;
import org.ourproject.kune.platf.server.manager.PropertyManager;
import org.ourproject.kune.platf.server.manager.PropertySubgroupManager;

import cc.kune.domain.Properties;
import cc.kune.domain.Property;
import cc.kune.domain.PropertyGroup;
import cc.kune.domain.PropertySetted;
import cc.kune.domain.PropertySubgroup;

import com.google.inject.Inject;

public class PropertySettedManagerDefaultTest extends PersistenceTest {

    private static final String NAME1 = "name1";
    private static final String DISPLAYNAME1 = "display test name 1";
    private static final String DEFVALUE1 = "def value 1";
    private static final String NAME2 = "name2";
    private static final String DISPLAYNAME2 = "display test name 2";
    private static final String DEFVALUE2 = "def value 2";
    private static final String NAME3 = "name3";
    private static final String DISPLAYNAME3 = "display test name 3";
    private static final String DEFVALUE3 = "def value 3";
    private static final String SOME_NEW_VALUE = "some new value";
    private static final String SOME_OTHER_NEW_VALUE = "some new value";

    @Inject
    PropertyManager propertyManager;

    @Inject
    PropertiesManager propertiesManager;

    @Inject
    PropertyGroupManager propGroupManager;

    @Inject
    PropertySubgroupManager propSubgroupManager;

    @Inject
    Property propFinder;

    @Inject
    PropertySetted propSettedFinder;

    @Inject
    PropertyGroup propGroupFinder;

    @Inject
    PropertySubgroup propSubgroupFinder;

    private Property prop1;
    private Property prop2;
    private Property prop3;
    private PropertyGroup somegroup;
    private PropertySubgroup somesubgroup;
    private PropertyGroup othergroup;

    @Before
    public void before() {
        openTransaction();
        somegroup = new PropertyGroup("somegroup");
        othergroup = new PropertyGroup("othergroup");
        somesubgroup = new PropertySubgroup("somesubgroup");
        propGroupManager.persist(somegroup);
        propGroupManager.persist(othergroup);
        propSubgroupManager.persist(somesubgroup);
        prop1 = new Property(NAME1, DISPLAYNAME1, Property.Type.STRING, true, DEFVALUE1, somegroup, somesubgroup);
        prop2 = new Property(NAME2, DISPLAYNAME2, Property.Type.STRING, false, DEFVALUE2, somegroup, somesubgroup);
        prop3 = new Property(NAME3, DISPLAYNAME3, Property.Type.STRING, false, DEFVALUE3, somegroup, somesubgroup);
        propertyManager.persist(prop1);
        propertyManager.persist(prop2);
        propertyManager.persist(prop3);
        assertEquals(3, propFinder.getAll().size());
    }

    @After
    public void close() {
        if (getTransaction().isActive()) {
            rollbackTransaction();
        }
    }

    @Test
    public void setProperty() {
        final Properties prop = new Properties(somegroup);
        assertEquals(DEFVALUE1, propertiesManager.get(prop, NAME1).getValue());
        assertEquals(0, prop.getList().size());
        propertiesManager.set(prop, NAME1, SOME_NEW_VALUE);
        assertNotNull(prop.getId());
        assertEquals(1, prop.getList().size());
        assertEquals(SOME_NEW_VALUE, propertiesManager.get(prop, NAME1).getValue());
        propertiesManager.set(prop, NAME1, SOME_OTHER_NEW_VALUE);
        assertEquals(1, prop.getList().size());
        assertEquals(SOME_OTHER_NEW_VALUE, propertiesManager.get(prop, NAME1).getValue());
    }

    @Test
    public void setTwoProps() {
        final Properties prop1 = new Properties(somegroup);
        final Properties prop2 = new Properties(somegroup);
        propertiesManager.persist(prop1);
        propertiesManager.persist(prop2);
        propertiesManager.set(prop1, NAME1, SOME_NEW_VALUE);
        propertiesManager.set(prop2, NAME1, SOME_OTHER_NEW_VALUE);
        assertEquals(2, propSettedFinder.getAll().size());
        assertEquals(SOME_NEW_VALUE, propertiesManager.get(prop1, NAME1).getValue());
        assertEquals(SOME_OTHER_NEW_VALUE, propertiesManager.get(prop2, NAME1).getValue());
    }

    @Test
    public void testGroupFinder() {
        assertEquals(3, propFinder.find(somegroup).size());
        assertEquals(0, propFinder.find(othergroup).size());
        assertEquals(3, propFinder.find(somesubgroup).size());
    }

    @Test
    public void testGroupRemove() {
        final Properties prop = new Properties(somegroup);
        propertiesManager.set(prop, NAME1, SOME_NEW_VALUE);
        assertEquals(1, propSettedFinder.getAll().size());

        assertNotNull(somegroup.getId());
        propGroupManager.remove(somegroup);
        closeTransaction();
        assertEquals(0, propFinder.getAll().size());
        assertEquals(0, propFinder.find(somegroup).size());
        assertEquals(1, propGroupFinder.getAll().size());
        assertEquals(0, propSettedFinder.getAll().size());
    }

    @Test
    public void testPropertiesRemove() {
        final Properties prop = new Properties(somegroup);
        propertiesManager.set(prop, NAME1, SOME_NEW_VALUE);
        assertEquals(1, propSettedFinder.getAll().size());
        propertiesManager.remove(prop);
        closeTransaction();
        assertEquals(0, propSettedFinder.getAll().size());
    }

    @Test
    public void testPropertyGet() {
        final Properties prop = new Properties(somegroup);
        propertiesManager.set(prop, NAME1, SOME_NEW_VALUE);
        final HashMap<String, PropertySetted> result = propertiesManager.get(prop);
        assertEquals(3, result.size());
        assertEquals(SOME_NEW_VALUE, result.get(NAME1).getValue());
    }

    @Test
    public void testSubgroupRemove() {
        assertNotNull(somesubgroup.getId());
        propSubgroupManager.remove(somesubgroup);
        closeTransaction();
        assertEquals(0, propFinder.getAll().size());
        assertEquals(2, propGroupFinder.getAll().size());
        assertEquals(0, propSubgroupFinder.getAll().size());
    }

}
