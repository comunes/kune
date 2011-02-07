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
package org.ourproject.kune.workspace.client.options.tools;

import java.util.HashMap;

import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.options.EntityOptionsView;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.shared.dto.ToolSimpleDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.Hidden;
import com.gwtext.client.widgets.form.Label;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;

public class EntityOptionsToolsConfPanel extends DefaultForm implements EntityOptionsToolsConfView {

    private final EntityOptionsToolsConfPresenter presenter;
    private final HashMap<String, Checkbox> fields;
    private final I18nTranslationService i18n;

    public EntityOptionsToolsConfPanel(final EntityOptionsToolsConfPresenter presenter, final WorkspaceSkeleton ws,
            final I18nTranslationService i18n) {
        super(i18n.t("Tools"));
        this.i18n = i18n;
        super.setHeight(EntityOptionsView.HEIGHT);
        super.setFrame(true);
        super.add(new Hidden());
        super.getFormPanel().setLabelWidth(20);
        this.presenter = presenter;
        fields = new HashMap<String, Checkbox>();
    }

    public void add(final ToolSimpleDTO tool) {
        final Checkbox checkbox = new Checkbox(tool.getRootName());
        checkbox.setChecked(false);
        checkbox.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(final Checkbox field, final boolean checked) {
                presenter.onCheck(tool, checked);
            }
        });
        super.add(checkbox);
        fields.put(tool.getName(), checkbox);
        doLayoutIfNeeded();
    }

    public void clear() {
        super.getFormPanel().removeAll(true);
        fields.clear();
        final Label label = new Label();
        label.setHtml(i18n.t("Here you can select the tools used:") + "<br/><br/>");
        super.add(label);
    }

    public void setChecked(final String tool, final boolean checked) {
        final Checkbox field = getTool(tool);
        field.setValue(checked);
    }

    public void setEnabled(final String tool, final boolean enabled) {
        final Checkbox field = getTool(tool);
        if (enabled) {
            field.enable();
        } else {
            field.disable();
        }
    }

    public void setTooltip(final String tool, final String tip) {
        final Checkbox field = getTool(tool);
        final ToolTip tooltip = new ToolTip();
        tooltip.setHtml(tip);
        tooltip.setWidth(250);
        tooltip.applyTo(field);
        field.setTitle(tip);
        doLayoutIfNeeded();
    }

    private void doLayoutIfNeeded() {
        if (super.getFormPanel().isRendered()) {
            super.getFormPanel().doLayout();
        }
    }

    private Checkbox getTool(final String tool) {
        final Checkbox field = fields.get(tool);
        if (field == null) {
            Log.error("Field " + tool + " not found in EOTCP");
        }
        return field;
    }
}
