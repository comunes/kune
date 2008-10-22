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
/*
 * GWT-Ext Widget Library
 * Copyright(c) 2007-2008, GWT-Ext.
 * licensing@gwt-ext.com
 *
 * http://www.gwt-ext.com/license
 */
package org.ourproject.kune.platf.client.ui.imgchooser;

import java.util.HashMap;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.core.NameValuePair;
import com.gwtext.client.core.RegionPosition;
import com.gwtext.client.core.XTemplate;
import com.gwtext.client.data.Store;
import com.gwtext.client.util.Format;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.DataView;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.ToolbarButton;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.DataViewListenerAdapter;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.BorderLayoutData;

public class ImageChooser extends Window {

    private Panel eastPanel;
    private Panel centerPanel;

    private TextField searchField;
    private Button okButton;

    // the view of the area displaying the different images
    private DataView view;

    // the callback once the ok is pressed. This indicates the caller what image
    // was selected
    private ImageChooserCallback callback;

    // the data about the selected image passed to the callback method
    private ImageData imageData;

    private final HashMap<String, ImageData> imageMap;
    private final Store store;

    public ImageChooser(final String title, final int minWidth, final int minHeight, final Store store) {
        imageMap = new HashMap<String, ImageData>();
        this.store = store;
        initMainPanel(title, minWidth, minHeight);
        createView();
        centerPanel.add(view);
    }

    public void show(final ImageChooserCallback callback) {
        this.callback = callback;
        super.show();
    }

    private void addCancelButton() {
        final Button cancelButton = new Button("Cancel");
        cancelButton.enable();
        cancelButton.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                hide();
            }
        });
        addButton(cancelButton);
        cancelButton.focus();
    }

    private void addOkButton() {
        okButton = new Button("Ok");
        okButton.disable();
        okButton.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                hide();
                if (callback != null) {
                    // pass the image data to the caller
                    callback.onImageSelection(imageData);
                }
            }
        });
        addButton(okButton);
    }

    /**
     * This method creates the two view for displaying the images. The main view
     * is the one that displays all the images to select. The second view
     * displays the selected images with information about the image.
     */
    private void createView() {
        // the thumb nail template for the main view
        final String thumbTemplate[] = new String[] { "<tpl for='.'>", "<div class='thumb-wrap' id='{name}'>",
                "<div class='thumb'><img src='{url}' title='{name}'></div>", "<span>{shortName}</span></div>",
                "</tpl>", "<div class='x-clear'></div>" };

        // the detail template for the selected image
        final String detailTemplate[] = new String[] { "<tpl for='.'>", "<div class='details'><img src='{url}'>",
                "<div class='details-info'><b>Image Name:</b>", "<span>{name}</span><b>Size:</b>",
                "<span>{sizeString}</span><b>Last Modified:</b>", "<span>{dateString}</span></div></div>", "</tpl>",
                "<div class='x-clear'></div>" };

        // compile the templates
        final XTemplate thumbsTemplate = new XTemplate(thumbTemplate);
        final XTemplate detailsTemplate = new XTemplate(detailTemplate);
        thumbsTemplate.compile();
        detailsTemplate.compile();

        // initialize the View using the thumb nail template
        view = new DataView("div.thumb-wrap") {
            public void prepareData(final Data data) {
                ImageData newImageData = null;
                final String name = data.getProperty("name");
                final String sizeString = data.getProperty("size");
                final String dateString = Format.date(data.getProperty("lastmod"), "m/d/Y g:i a");

                data.setProperty("shortName", Format.ellipsis(data.getProperty("name"), 15));
                data.setProperty("sizeString", sizeString);

                if (imageMap.containsKey(name)) {
                    newImageData = imageMap.get(name);
                } else {
                    newImageData = new ImageData();
                    imageMap.put(name, newImageData);
                }

                newImageData.setFileName(name);
                newImageData.setName(name);
                newImageData.setLastModified(dateString);
                newImageData.setSize(Long.parseLong(data.getProperty("size")));
                newImageData.setUrl(data.getProperty("url"));
            }
        };
        view.setSingleSelect(true);
        view.setTpl(thumbsTemplate);
        view.setStore(store);
        view.setAutoHeight(true);
        view.setOverCls("x-view-over");

        // if there is no images that can be found, just output an message
        view.setEmptyText("<div style=\"padding:10px;\">No images match the specified filter</div>");
        view.addListener(new DataViewListenerAdapter() {

            /**
             * This method is called when a selection is made changing the
             * previous selection
             * 
             * @params view the view that this selection is for
             * @params selections a list of all selected items. There should
             *         only be one as we only allow 1 selection.
             */
            public void onSelectionChange(final DataView component, final Element[] selections) {
                // The details Ext.Element
                final ExtElement detailEl = eastPanel.getEl();

                // if there is a selection and show the details
                if (selections != null && selections.length > 0 && selections[0] != null) {
                    // enable the ok button now there is a selection made
                    okButton.enable();
                    final String id = DOM.getElementAttribute(selections[0], "id");
                    imageData = imageMap.get(id);

                    detailEl.hide();
                    final NameValuePair vals[] = new NameValuePair[4];
                    vals[0] = new NameValuePair("name", imageData.getName());
                    vals[1] = new NameValuePair("url", imageData.getUrl());
                    vals[2] = new NameValuePair("sizeString", imageData.getSize());
                    vals[3] = new NameValuePair("dateString", imageData.getLastModified());
                    final String html = detailsTemplate.applyTemplate(vals);
                    detailEl.update(html);
                    detailEl.slideIn();
                } else {
                    // no selection means the ok button should be disabled and
                    // the detail
                    // area should be blanked out
                    okButton.disable();
                    detailEl.update("");
                }
            }
        });
    }

    private void displayThumbs(final String findStr) {
        if (findStr == null || findStr.equals("")) {
            store.clearFilter(true);
        } else {
            store.filter("name", findStr, true);
        }
        view.refresh();
    }

    /**
     * This method creates the toolbar for the dialog.
     * 
     * @return the toolbar just created to be added into the dialog
     */
    private Toolbar getToolbar() {
        final Toolbar tb = new Toolbar();
        searchField = new TextField();

        searchField.setId("ychooser-toolbar-searchfield");
        searchField.setMaxLength(60);
        searchField.setGrow(false);
        searchField.setSelectOnFocus(true);

        searchField.addListener(new FieldListenerAdapter() {

            /**
             * This method will be called when special characters are pressed.
             * This method is only interested in the enter key so that it can
             * load the images. It simulates pressing the "Find" button.
             */
            public void onSpecialKey(final Field field, final EventObject e) {
                if (e.getKey() == EventObject.ENTER) {
                    displayThumbs(searchField.getValueAsString()); // load the
                    // images in
                    // the view
                }
            }
        });

        tb.addField(searchField);

        final ToolbarButton tbb = new ToolbarButton("Find");
        tbb.setIconCls("search-icon");
        tbb.addListener(new ButtonListenerAdapter() {
            public void onClick(final Button button, final EventObject e) {
                displayThumbs(searchField.getValueAsString());
            }
        });
        tb.addButton(tbb);
        return tb;
    }

    private void initMainPanel(final String title, final int minWidth, final int minHeight) {
        setLayout(new BorderLayout());
        setHeight(minHeight);
        setWidth(minWidth);
        setTitle(title);
        addClass("ychooser-dlg");

        eastPanel = new Panel();
        eastPanel.setId("east-panel");
        eastPanel.setCollapsible(false);
        eastPanel.setWidth(150);
        eastPanel.setPaddings(5);

        centerPanel = new Panel();
        centerPanel.setId("ychooser-view");
        centerPanel.setCollapsible(false);
        centerPanel.setWidth(100);
        centerPanel.setHeight(200);
        centerPanel.setAutoScroll(true);

        add(getToolbar(), new BorderLayoutData(RegionPosition.NORTH));
        add(centerPanel, new BorderLayoutData(RegionPosition.CENTER));
        add(eastPanel, new BorderLayoutData(RegionPosition.EAST));

        addOkButton();
        addCancelButton();
    }
}
