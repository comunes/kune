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
package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;

public class InsertImageAbstractPresenter implements InsertImageAbstract {

    protected InsertImageAbstractView view;
    protected final InsertImageDialog insertImageDialog;
    private final Listener0 onInsertImagePressed;
    protected ImageInfo imageInfo;

    public InsertImageAbstractPresenter(final InsertImageDialog insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
        onInsertImagePressed = new Listener0() {
            public void onEvent() {
                if (isValid()) {
                    ImageInfo linkInfo = updateImageInfo();
                    insertImageDialog.fireOnInsertImage(linkInfo);
                } else {
                    Log.debug("Form in InsertImage not valid");
                }
            }
        };
    }

    public ImageInfo getImageInfo() {
        return insertImageDialog.getImageInfo();
    }

    public View getView() {
        return view;
    }

    public void init(final InsertImageAbstractView view) {
        this.view = view;
        insertImageDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        insertImageDialog.setOnInsertImagePressed(onInsertImagePressed);
    }

    public void onClickOriginalChecked(final boolean checked) {
        insertImageDialog.getImageInfo().setClickOriginal(checked);
    }

    public void onPositionFieldChanged(final String position) {
        insertImageDialog.getImageInfo().setPosition(position);
    }

    public void onSizeFieldChanged(final String size) {
        insertImageDialog.getImageInfo().setSize(size);
    }

    public void onWrapTextChecked(final boolean checked) {
        insertImageDialog.getImageInfo().setWraptext(checked);
    }

    public void reset() {
        view.reset();
    }

    public void setPosition(final String position) {
        insertImageDialog.getImageInfo().setPosition(position);
    }

    public void setSize(final String size) {
        insertImageDialog.getImageInfo().setSize(size);
    }

    protected ImageInfo updateImageInfo() {
        return new ImageInfo(view.getSrc(), view.getWrapText(), view.getClickOriginal(), view.getPosition(),
                view.getSize());
    }
}
