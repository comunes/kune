package org.ourproject.kune.blogs.client;

import org.ourproject.kune.blogs.client.cnt.BlogContent;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.blogs.client.ui.BlogFactory;

class BlogToolComponents {
    private BlogContent content;
    private BlogContext context;
    private final BlogClientTool blogClientTool;

    public BlogToolComponents(final BlogClientTool blogClientTool) {
        this.blogClientTool = blogClientTool;
    }

    public BlogContent getContent() {
        if (content == null) {
            content = BlogFactory.createDocumentContent(blogClientTool);
        }
        return content;
    }

    public BlogContext getContext() {
        if (context == null) {
            context = BlogFactory.createDocumentContext();
        }
        return context;
    }

}
