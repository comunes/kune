package org.ourproject.kune.platf.server.domain;

import org.hibernate.search.bridge.builtin.StringBridge;

public class DataFieldBridge extends StringBridge {
    public String objectToString(final Object object) {
        if (object != null) {
            return new String((char[]) object);
        } else {
            return null;
        }
    }
}
