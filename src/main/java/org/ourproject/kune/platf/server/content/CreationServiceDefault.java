package org.ourproject.kune.platf.server.content;

import org.ourproject.kune.platf.server.domain.Container;
import org.ourproject.kune.platf.server.domain.Content;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.domain.User;
import org.ourproject.kune.platf.server.tool.ToolRegistry;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CreationServiceDefault implements CreationService {
    private final ContainerManager containerManager;
    private final ContentManager contentManager;
    private final ToolRegistry tools;

    @Inject
    public CreationServiceDefault(final ContainerManager containerManager, final ContentManager contentManager,
	    final ToolRegistry toolRegistry) {
	this.containerManager = containerManager;
	this.contentManager = contentManager;
	this.tools = toolRegistry;
    }

    public Content createContent(final String title, final User user, final Container container) {
	String toolName = container.getToolName();
	Content content = contentManager.createContent(title, user, container);
	tools.get(toolName).onCreateContent(content, container);
	return content;
    }

    public Container createFolder(final Group group, final Long parentFolderId, final String name) {
	Container parent = containerManager.find(parentFolderId);
	String toolName = parent.getToolName();
	Container child = containerManager.createFolder(group, parent, name);
	tools.get(toolName).onCreateContainer(child, parent);
	return child;
    }

    public Content saveContent(final Group userGroup, final Content descriptor, final String content) {
	return contentManager.save(userGroup, descriptor, content);
    }

}
