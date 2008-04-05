package org.ourproject.kune.blogs.client;

import org.ourproject.kune.blogs.client.cnt.BlogContent;
import org.ourproject.kune.blogs.client.cnt.BlogContentListener;
import org.ourproject.kune.blogs.client.ctx.BlogContext;
import org.ourproject.kune.platf.client.dto.StateDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.tool.AbstractClientTool;
import org.ourproject.kune.workspace.client.component.WorkspaceComponent;

public class BlogClientTool extends AbstractClientTool implements BlogContentListener {
    public static final String TYPE_ROOT = "blogs.root";
    public static final String TYPE_FOLDER = "blogs.blog";
    public static final String TYPE_DOCUMENT = "blogs.post";
    public static final String NAME = "blogs";
    private final BlogToolComponents components;

    public BlogClientTool() {
        super(Kune.I18N.t("blogs"));
        components = new BlogToolComponents(this);
    }

    public WorkspaceComponent getContent() {
        return components.getContent();
    }

    public WorkspaceComponent getContext() {
        return components.getContext();
    }

    public String getName() {
        return NAME;
    }

    public void setContent(final StateDTO state) {
        BlogContent docContent = components.getContent();
        docContent.setContent(state);
        trigger.setState(state.getStateToken().toString());
    }

    public void setContext(final StateDTO state) {
        BlogContext context = components.getContext();
        context.setContext(state);
    }

    public void onEdit() {
        components.getContext().showAdmin();
    }

    public void onCancel() {
        components.getContext().showFolders();
    }

}
