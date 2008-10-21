package org.ourproject.kune.platf.client.utils;

import java.util.ArrayList;

public class Url {
    private final String base;
    private final ArrayList<UrlParam> params;

    public Url(String base) {
        this.base = base;
        params = new ArrayList<UrlParam>();
    }

    public Url(String base, UrlParam... iniParams) {
        this(base);
        for (UrlParam param : iniParams) {
            params.add(param);
        }
    }

    @Override
    public String toString() {
        String paramPart = "";
        boolean first = true;
        for (UrlParam param : params) {
            if (first) {
                paramPart = "?" + param;
                first = false;
            } else {
                paramPart += "&" + param;
            }
        }
        return base + paramPart;
    }
}
