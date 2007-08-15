package org.ourproject.kune.docs.client.ui.forms.newdoc;

import org.ourproject.kune.platf.client.View;

public interface NewDocumentForm {
    View getView();

    String getName();

    void setCommandLabels(String string, String string2);
}
