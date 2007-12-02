package org.ourproject.rack.filters.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.ourproject.rack.filters.rest2.TestJSONMethodFinder.MyTestService;
import org.ourproject.rack.filters.rest2.TestJSONMethodFinder.TestParameters;

public class TestRESTMethodFinder {
	private RESTMethodFinder finder;
	private MyTestService service;

	@Before
	public void createObjects() {
		this.finder = new DefaultRESTMethodFinder();
		this.service = new MyTestService();
	}

	
	@Test
	public void simpleTest() {
		JsonMethod method = finder.findMethod("simpleMethod", new TestParameters("name", "theName"), MyTestService.class);
		assertNotNull(method);
		assertTrue(method.invoke(service));
		assertEquals("the name: theName", method.getResponse().toString());
	}
	
	@Test
	public void conversionTest() {
		JsonMethod method = finder.findMethod("convertIntMethod", new TestParameters("length", "12"), MyTestService.class);
		assertNotNull(method);
		assertTrue(method.invoke(service));
		assertEquals("the length: 12", method.getResponse().toString());
	}
	
	public static class MyTestService {
		@RESTMethod(params={"name"})
		public String simpleMethod(String name) {
			return "the name: " + name;
		}
		
		@RESTMethod(params={"length"}) 
		public String convertIntMethod(int length) {
			return "the length: " + length;
		}
	}
	
	public static class TestParameters implements Parameters {
		private final HashMap<String, String> map;

		public TestParameters(String ...pairs) {
			this.map = new HashMap<String, String>();
			for (int index = 0; index < pairs.length; index += 2) {
				map.put(pairs[index], pairs[index + 1]);
			}
		}

		public String get(String name) {
			return map.get(name);
		}
		
		public int getSize() {
			return map.size();
		}
	}
}
