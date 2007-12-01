package org.ourproject.rack.filters.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TestJSONMethodFinder {
	
	private JSONMethodFinder finder;
	private MyTestService service;

	@Before
	public void createObjects() {
		this.finder = new DefaultJSONMethodFinder();
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
		JsonMethod method = finder.findMethod("convertMethod", new TestParameters("length", "12"), MyTestService.class);
		assertNotNull(method);
		assertTrue(method.invoke(service));
		assertEquals("the length: 12", method.getResponse().toString());
	}
	
	public static class MyTestService {
		@JSONMethod(name="simpleMethod", params={"name"})
		public String method1(String name) {
			return "the name: " + name;
		}
		
		@JSONMethod(name="convertMethod", params={"length"}) 
		public String method2(int length) {
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
