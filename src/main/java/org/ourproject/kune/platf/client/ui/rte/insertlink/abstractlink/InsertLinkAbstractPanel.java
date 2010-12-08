package org.ourproject.kune.platf.client.ui.rte.insertlink.abstractlink;

import org.ourproject.kune.platf.client.i18n.Resources;
import org.ourproject.kune.platf.client.ui.dialogs.DefaultForm;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialogView;
import org.ourproject.kune.platf.client.ui.rte.insertlink.LinkInfo;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtext.client.core.EventCallback;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.form.Checkbox;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.CheckboxListenerAdapter;
import com.gwtext.client.widgets.form.event.FormPanelListenerAdapter;

public class InsertLinkAbstractPanel extends DefaultForm {

    protected TextField textField;
    protected TextField hrefField;
    protected TextField onOverField;
    protected final Checkbox sameWindow;

    public InsertLinkAbstractPanel(final String title, final InsertLinkAbstractPresenter presenter) {
        super(title);
        super.setAutoWidth(true);
        super.setHeight(InsertLinkDialogView.HEIGHT);

        hrefField = new TextField();
        hrefField.setTabIndex(1);
        hrefField.setWidth(DEF_FIELD_WIDTH);
        hrefField.setAllowBlank(false);
        hrefField.setMinLength(3);
        hrefField.setMaxLength(250);
        hrefField.setValidationEvent(false);

        textField = new TextField();
        textField.setTabIndex(2);
        textField.setFieldLabel(Resources.i18n.t("Text"));
        textField.setWidth(DEF_FIELD_WIDTH);
        textField.setValidationEvent(false);
        textField.setTitle(Resources.i18n.t("The hyper-linked text, like: ")
                + "<span style=\"font-color:navy !important; text-decoration:underline;\">"
                + Resources.i18n.t("Click me for more information!"));

        onOverField = new TextField();
        onOverField.setTabIndex(3);
        onOverField.setFieldLabel(Resources.i18n.t("Flyover"));
        onOverField.setWidth(DEF_FIELD_WIDTH);
        onOverField.setValidationEvent(false);
        onOverField.setTitle(Resources.i18n.t("The flyover appears when the viewer's mouse cursor is over the link."));

        sameWindow = new Checkbox(Resources.i18n.t("Open link in new window"));
        sameWindow.setChecked(false);

        textField.addKeyPressListener(new EventCallback() {
            public void execute(final EventObject e) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    public void execute() {
                        presenter.onTextFieldChanged(textField.getRawValue());
                    }
                });
            }
        });

        onOverField.addKeyPressListener(new EventCallback() {
            public void execute(final EventObject e) {
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {
                    public void execute() {
                        presenter.onOverFieldChanged(onOverField.getRawValue());
                    }
                });
            }
        });

        sameWindow.addListener(new CheckboxListenerAdapter() {
            @Override
            public void onCheck(final Checkbox field, final boolean checked) {
                presenter.onSameWindowCheck(checked);
            }
        });

        super.addListener(new FormPanelListenerAdapter() {
            @Override
            public void onActivate(final Panel panel) {
                final LinkInfo linkInfo = presenter.getLinkInfo();
                updateValues(linkInfo);
                presenter.onActivate();
            }
        });

        final FieldSet advanced = new FieldSet(Resources.i18n.t("More options"));
        advanced.setCollapsible(true);
        advanced.setCollapsed(true);
        advanced.setAutoHeight(true);

        advanced.add(onOverField);
        advanced.add(sameWindow);
        add(hrefField);
        add(textField);
        add(advanced);
    }

    public String getHref() {
        return hrefField.getRawValue();
    }

    public String getText() {
        return textField.getRawValue();
    }

    public String getTitle() {
        return onOverField.getRawValue();
    }

    public boolean inSameWindow() {
        return sameWindow.getValue();
    }

    @Override
    public void insert(final int index, final Component component) {
        super.insert(index, component);
    }

    protected void updateValues(final LinkInfo linkInfo) {
        textField.setValue(linkInfo.getText());
        onOverField.setValue(linkInfo.getTitle());
        if (sameWindow.isVisible()) {
            sameWindow.setChecked(linkInfo.inSameWindow());
        }
    }
}
