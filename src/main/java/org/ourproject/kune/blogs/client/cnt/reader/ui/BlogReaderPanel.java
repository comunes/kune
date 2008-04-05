package org.ourproject.kune.blogs.client.cnt.reader.ui;

import org.ourproject.kune.blogs.client.cnt.reader.BlogReaderView;
import org.ourproject.kune.workspace.client.workspace.ui.DefaultContentPanel;

public class BlogReaderPanel extends DefaultContentPanel implements BlogReaderView {

    public BlogReaderPanel() {
        setContent("This is the content");
    }
}
