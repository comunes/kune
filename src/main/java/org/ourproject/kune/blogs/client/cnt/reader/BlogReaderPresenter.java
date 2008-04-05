package org.ourproject.kune.blogs.client.cnt.reader;

import org.ourproject.kune.platf.client.View;

public class BlogReaderPresenter implements BlogReader {
    private final BlogReaderView view;

    public BlogReaderPresenter(final BlogReaderView view) {
        this.view = view;
    }

    public void showDocument(final String text) {
        view.setContent(text);
    }

    public View getView() {
        return view;
    }
}
