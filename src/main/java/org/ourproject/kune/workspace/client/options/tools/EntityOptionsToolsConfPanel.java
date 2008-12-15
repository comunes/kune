package org.ourproject.kune.workspace.client.options.tools;

import java.util.HashMap;

import org.ourproject.kune.platf.client.dto.ToolSimpleDTO;
import org.ourproject.kune.platf.client.services.I18nTranslationService;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

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
            I18nTranslationService i18n) {
        super(i18n.t("Tools"));
        this.i18n = i18n;
        super.setAutoWidth(true);
        super.setAutoHeight(true);
        super.add(new Hidden());
        super.getFormPanel().setLabelWidth(20);
        this.presenter = presenter;
        fields = new HashMap<String, Checkbox>();
    }

    public void add(final ToolSimpleDTO tool) {
        Checkbox checkbox = new Checkbox(tool.getRootName(), new CheckboxListenerAdapter() {
            @Override
            public void onCheck(Checkbox field, boolean checked) {
                presenter.onCheck(tool, checked);
            }
        });
        checkbox.setChecked(false);
        super.add(checkbox);
        fields.put(tool.getName(), checkbox);
        doLayoutIfNeeded();
    }

    public void clear() {
        super.getFormPanel().removeAll(true);
        fields.clear();
        Label label = new Label();
        label.setHtml(i18n.t("Here you can select the tools used:") + "<br/><br/>");
        super.add(label);
    }

    public void setChecked(String tool, boolean checked) {
        Checkbox field = getTool(tool);
        field.setValue(checked);
    }

    public void setEnabled(String tool, boolean enabled) {
        Checkbox field = getTool(tool);
        if (enabled) {
            field.enable();
        } else {
            field.disable();
        }
    }

    public void setTooltip(String tool, String tip) {
        Checkbox field = getTool(tool);
        ToolTip tooltip = new ToolTip();
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

    private Checkbox getTool(String tool) {
        Checkbox field = fields.get(tool);
        if (field == null) {
            Log.error("Field " + tool + " not found in EOTCP");
        }
        return field;
    }
}
