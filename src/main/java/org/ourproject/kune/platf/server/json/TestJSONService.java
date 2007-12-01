package org.ourproject.kune.platf.server.json;

import java.util.Date;

import org.ourproject.rack.filters.json.JSONMethod;

import com.google.inject.Inject;

public class TestJSONService {

	@Inject
	public TestJSONService() {
	}

	@JSONMethod(name = "test", params = { "value" })
	public String test(String value) {
		return "The value is " + value;
	}

	@JSONMethod(name = "testObject", params = { "name", "value" })
	public SimpleObject test(String theName, String theValue) {
		return new SimpleObject(theName, theValue, new Date());
	}

	public static class SimpleObject {
		private String name;
		private String value;
		private Date date;

		public SimpleObject(String name, String value, Date date) {
			this.name = name;
			this.value = value;
			this.date = date;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

	}
}
