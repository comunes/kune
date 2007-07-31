package org.ourproject.kune.sitebar.client;

import com.google.gwt.core.client.GWT;

public class SiteBar {
        private static SiteBar instance;
        public Translate t;

        private SiteBar() {
            t = (Translate) GWT.create(Translate.class);
        }

        public static SiteBar getInstance() {
            if (instance == null) {
                instance = new SiteBar();
            }
            return instance;
        }

}
