package org.ourproject.kune.platf.server.domain;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Indexed;

@Entity
@Indexed
@Table(name = "customproperties")
public class CustomProperties implements HasId {

    @Id
    @DocumentId
    @GeneratedValue
    private Long id;

    @Lob
    private HashMap<Class<?>, Object> data;

    public CustomProperties() {
        data = new HashMap<Class<?>, Object>();
    }

    public HashMap<Class<?>, Object> getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getData(final Class<T> type) {
        return (T) data.get(type);
    }

    public Long getId() {
        return id;
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

    public void setId(final Long id) {
        this.id = id;
    }
}
