package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import java.util.ArrayList;
import java.util.Iterator;

public class ExternalMediaRegistry extends ArrayList<ExternalMediaDescriptor> {

    private static final long serialVersionUID = 1L;

    public static final ExternalMediaDescriptor NO_MEDIA = new ExternalMediaDescriptor(null, null, null, null, null);

    public ExternalMediaDescriptor get(final String url) {
        for (ExternalMediaDescriptor media : this) {
            if (media.is(url)) {
                return media;
            }

        }
        return NO_MEDIA;
    }

    public String getNames() {
        String names = "";
        for (Iterator<ExternalMediaDescriptor> iterator = this.iterator(); iterator.hasNext();) {
            ExternalMediaDescriptor elem = iterator.next();
            names += elem.getName();
            if (iterator.hasNext()) {
                names += ", ";
            }
        }
        return names;
    }
}