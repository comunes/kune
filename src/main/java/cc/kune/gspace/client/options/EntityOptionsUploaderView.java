/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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

package cc.kune.gspace.client.options;

import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;

import com.google.gwt.event.shared.HandlerRegistration;

public interface EntityOptionsUploaderView {
  HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler);

  HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler);

  HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler);

  HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler);
}
