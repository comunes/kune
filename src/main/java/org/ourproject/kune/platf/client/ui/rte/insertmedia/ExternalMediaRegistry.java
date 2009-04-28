package org.ourproject.kune.platf.client.ui.rte.insertmedia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ourproject.kune.platf.client.dto.ExtMediaDescripDTO;
import org.ourproject.kune.platf.client.ui.TextUtils;

public class ExternalMediaRegistry extends ArrayList<ExtMediaDescripDTO> {

    private static final long serialVersionUID = 1L;

    public static final ExtMediaDescripDTO NO_MEDIA = new ExtMediaDescripDTO(null, null, null, null, null, 0, 0);

    public ExternalMediaRegistry(final List<ExtMediaDescripDTO> extMediaDescrips) {
        super(extMediaDescrips);
    }

    public ExtMediaDescripDTO get(final String url) {
        for (ExtMediaDescripDTO media : this) {
            if (media.is(url)) {
                return media;
            }

        }
        return NO_MEDIA;
    }

    public String getNames() {
        String names = "";
        for (Iterator<ExtMediaDescripDTO> iterator = this.iterator(); iterator.hasNext();) {
            ExtMediaDescripDTO elem = iterator.next();
            names += TextUtils.generateHtmlLink(elem.getSiteurl(), elem.getName());
            if (iterator.hasNext()) {
                names += ", ";
            }
        }
        return names;
    }
}