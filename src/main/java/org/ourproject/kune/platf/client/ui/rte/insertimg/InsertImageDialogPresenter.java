/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import cc.kune.core.client.ui.utils.ContentPosition;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertImageDialogPresenter extends AbstractTabbedDialogPresenter implements InsertImageDialog {

    private Listener<ImageInfo> onCreateListener;
    private ImageInfo imageInfo;
    private Listener0 onInsertPressed;

    public InsertImageDialogPresenter() {
        initImageInfo();
    }

    public void fireOnInsertImage(final ImageInfo imageInfo) {
        onCreateListener.onEvent(imageInfo);
        super.hide();
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void reset() {
        initImageInfo();
    }

    public void setOnCreateImage(final Listener<ImageInfo> listener) {
        onCreateListener = listener;
    }

    public void setOnInsertImagePressed(final Listener0 onInsertPressed) {
        this.onInsertPressed = onInsertPressed;
    }

    protected void onCancel() {
        super.hide();
    }

    protected void onInsert() {
        onInsertPressed.onEvent();
    }

    private void initImageInfo() {
        imageInfo = new ImageInfo("", ImageInfo.DEF_WRAP_VALUE, ImageInfo.DEF_CLICK_ORIGINAL_VALUE,
                ContentPosition.LEFT, ImageInfo.SIZE_ORIGINAL);
    }
}
