package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.ui.rte.saving.RTESavingEditor;

public interface ContentEditor extends RTESavingEditor {

    /**
     * Sets the "File" menu title (some times you want a "Post" or "Wikipage"
     * menu instead of a "File" menu).
     * 
     * @param title
     *            the new file menu title
     */
    void setFileMenuTitle(String title);

}
