package org.ourproject.kune.platf.client.utils;

public class UrlParam {
    private final String value;
    private final String name;

    public UrlParam(String name, boolean value) {
        this.name = name;
        this.value = value ? "true" : "false";
    }

    public UrlParam(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + "=" + value;
    }
}
