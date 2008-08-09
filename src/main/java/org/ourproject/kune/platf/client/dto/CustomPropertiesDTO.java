package org.ourproject.kune.platf.client.dto;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CustomPropertiesDTO implements IsSerializable {

    private HashMap<Class<?>, Object> data;

    public CustomPropertiesDTO() {
	data = new HashMap<Class<?>, Object>();
    }

    public HashMap<Class<?>, Object> getData() {
	return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(final Class<T> type) {
	return (T) data.get(type);
    }

    public <T> boolean hasPropertie(final Class<T> type) {
	return data.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public <T> T setData(final Class<T> type, final T value) {
	return (T) data.put(type, value);
    }

    public void setData(final HashMap<Class<?>, Object> data) {
	this.data = data;
    }

}
