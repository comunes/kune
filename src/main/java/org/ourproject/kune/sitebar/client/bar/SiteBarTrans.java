package org.ourproject.kune.sitebar.client.bar;

import org.ourproject.kune.sitebar.client.Translate;

import com.google.gwt.core.client.GWT;

public class SiteBarTrans {
        private static SiteBarTrans instance;
        public Translate t;

        private SiteBarTrans() {
            t = (Translate) GWT.create(Translate.class);
        }

        public static SiteBarTrans getInstance() {
            if (instance == null) {
                instance = new SiteBarTrans();
            }
            return instance;
        }

}
