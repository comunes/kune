package org.ourproject.kune.platf.server.model;

import org.ourproject.kune.platf.server.domain.ContentDescriptor;
import org.ourproject.kune.platf.server.domain.Folder;

public class Content {
    private ContentDescriptor descriptor;
    private Folder folder;

    public Content(final ContentDescriptor descriptor, final Folder folder) {
	this.descriptor = descriptor;
	this.folder = folder;
    }

    public ContentDescriptor getDescriptor() {
	return descriptor;
    }

    public void setDescriptor(final ContentDescriptor descriptor) {
	this.descriptor = descriptor;
    }

    public Folder getFolder() {
	return folder;
    }

    public void setFolder(final Folder folder) {
	this.folder = folder;
    }

}
