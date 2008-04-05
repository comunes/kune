
package org.ourproject.kune.docs.client.cnt.reader.ui;

import org.ourproject.kune.docs.client.cnt.reader.DocumentReaderView;
import org.ourproject.kune.workspace.client.workspace.ui.DefaultContentPanel;

public class DocumentReaderPanel extends DefaultContentPanel implements DocumentReaderView {

    public DocumentReaderPanel() {
        setContent("This is the content");
    }
}
