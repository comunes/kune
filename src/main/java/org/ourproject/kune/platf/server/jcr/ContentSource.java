package org.ourproject.kune.platf.server.jcr;


public interface ContentSource {
    Iterable<String> keys();

    String get(String key);

}
