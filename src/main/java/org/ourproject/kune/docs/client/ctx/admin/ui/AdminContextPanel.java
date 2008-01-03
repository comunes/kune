/*
 *
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.docs.client.ctx.admin.ui;

import java.util.Date;
import java.util.List;

import org.ourproject.kune.docs.client.ctx.admin.AdminContextPresenter;
import org.ourproject.kune.docs.client.ctx.admin.AdminContextView;
import org.ourproject.kune.platf.client.dto.AccessListsDTO;
import org.ourproject.kune.platf.client.dto.I18nLanguageDTO;
import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.stacks.IndexedStackPanel;
import org.ourproject.kune.workspace.client.ui.ctx.admin.AccessListsPanel;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.widgets.form.DateField;
import com.gwtext.client.widgets.form.DateFieldConfig;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class AdminContextPanel extends VerticalPanel implements AdminContextView {
    private final AccessListsPanel accessListsPanel;
    private final IndexedStackPanel options;
    private final AdminContextPresenter presenter;
    private DateField dateField;

    public AdminContextPanel(final AdminContextPresenter presenter) {
        this.presenter = presenter;
        options = new IndexedStackPanel();
        options.addStyleName("kune-AdminContextPanel");

        // i18n

        VerticalPanel vp = options.addStackItem("Authors", "Authors of this work", true);
        vp.add(new Label("bla, bla"));

        vp = options.addStackItem("Language", "Language of this content", false);
        vp.add(new Label("bla, bla"));

        vp = options.addStackItem("Publication", "If this work is public", false);
        VerticalPanel pubComponent = createPublicationComponent();
        vp.add(createPublicationComponent());

        vp = options.addStackItem("Tags", "Tags bla bla", true);
        vp.add(new Label("bla, bla"));

        vp = options.addStackItem(Kune.I18N.t("Permissions"), "Who can admin/edit/view this", false);
        accessListsPanel = new AccessListsPanel();
        vp.add(accessListsPanel);

        add(options);
        setCellWidth(options, "100%");
        setWidth("100%");
        accessListsPanel.addStyleName("kune-AdminContextPanel-inner");
        pubComponent.addStyleName("kune-AdminContextPanel-inner");
        // setCellWidth(accessListsPanel, "100%");
    }

    private VerticalPanel createPublicationComponent() {
        Form form = new Form(new FormConfig() {
            {
                setHideLabels(true);
            }
        });

        dateField = new DateField(new DateFieldConfig() {
            {
                setWidth("200");
                setFormat("Y-m-d");
            }
        });

        dateField.addFieldListener(new FieldListenerAdapter() {
            public void onChange(final Field field, final Object newVal, final Object oldVal) {
                presenter.setPublishedOn((Date) newVal);
            }
        });

        form.add(dateField);
        form.render();
        VerticalPanel vp = new VerticalPanel();
        vp.add(form);
        return vp;
    }

    public void setAccessLists(final AccessListsDTO accessLists) {
        accessListsPanel.setAccessLists(accessLists);
    }

    public void setAuthors(final List authors) {
        // TODO Auto-generated method stub

    }

    public void setLanguage(final I18nLanguageDTO language) {
        // TODO Auto-generated method stub

    }

    public void setPublishedOn(final Date publishedOn) {
        // publishedCombo.setValue(publishedOn);
    }

    public void setTags(final String tags) {
        // TODO Auto-generated method stub

    }
}
