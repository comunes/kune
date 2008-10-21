/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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

package org.ourproject.kune.docs.client.cnt.reader;

import org.ourproject.kune.docs.client.DocumentClientTool;
import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.dto.BasicMimeTypeDTO;
import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.ui.download.FileDownloadUtils;
import org.ourproject.kune.platf.client.ui.download.ImageSize;

import com.calclab.suco.client.ioc.Provider;

public class DocumentReaderPresenter implements DocumentReader {
    private final DocumentReaderView view;
    private final Provider<FileDownloadUtils> downloadProvider;

    public DocumentReaderPresenter(final DocumentReaderView view, final Provider<FileDownloadUtils> downloadProvider) {
        this.view = view;
        this.downloadProvider = downloadProvider;
    }

    public View getView() {
        return view;
    }

    public void showDocument(final StateToken token, final String text, final String typeId,
            final BasicMimeTypeDTO mimeType) {
        if (typeId.equals(DocumentClientTool.TYPE_UPLOADEDFILE)) {
            if (mimeType != null) {
                FileDownloadUtils fileDownloadUtils = downloadProvider.get();
                if (mimeType.getType().equals("image")) {
                    view.showImage(fileDownloadUtils.getImageUrl(token),
                                   fileDownloadUtils.getImageResizedUrl(token, ImageSize.sized));
                } else if (mimeType.toString().equals("text/plain") || mimeType.toString().equals("application/pdf")) {
                    view.setContent(text);
                } else {
                    view.setContent("");
                }
            }
        } else {
            view.setContent(text);
        }
    }
}
