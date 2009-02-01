/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
/*
 * GWT-Ext Widget Library
 * Copyright(c) 2007-2008, GWT-Ext.
 * licensing@gwt-ext.com
 *
 * http://www.gwt-ext.com/license
 */package org.ourproject.kune.platf.client.ui.imgchooser;

/**
 * Interface that provides the callback name so that it can be called once the
 * image is selected.
 * 
 * @author mlim
 */
public interface ImageChooserCallback {
    /**
     * This method will be called by the dialog upon an image been selected
     * 
     * @param data
     *            is the data information of the selected image
     */
    void onImageSelection(ImageData data);
}
