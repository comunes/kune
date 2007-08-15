package org.ourproject.kune.docs.client.ui.ctx.folder;

import java.util.List;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.AccessRightsDTO;
import org.ourproject.kune.platf.client.dto.ContentDescriptorDTO;
import org.ourproject.kune.platf.client.dto.FolderDTO;

public class FolderContextPresenter implements FolderContext {
    private final FolderContentView view;

    public FolderContextPresenter(final FolderContentView view) {
	this.view = view;
    }

    public View getView() {
	return view;
    }

    public void setFolder(final FolderDTO folder, final AccessRightsDTO rights) {
	List contents = folder.getContents();
	view.clear();
	for (int index = 0; index < contents.size(); index++) {
	    ContentDescriptorDTO dto = (ContentDescriptorDTO) contents.get(index);
	    view.add(dto.getTitle(), "file", "");
	}
	view.setAddControlsVisibles(rights.isEditable, rights.isEditable);
    }
}
