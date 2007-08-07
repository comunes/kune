package org.ourproject.kune.platf.server.jcr;

public interface ContentDestination {
    Iterable<String> keys();

    void put(String key, String value);
}
