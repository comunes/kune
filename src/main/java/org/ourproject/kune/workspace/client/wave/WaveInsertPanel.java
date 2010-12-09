package org.ourproject.kune.workspace.client.wave;

import org.ourproject.kune.platf.client.ui.img.ImgConstants;
import org.ourproject.kune.platf.client.ui.img.ImgResources;

import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.emiteuimodule.client.dialog.BasicDialogExtended;
import com.calclab.emiteuimodule.client.dialog.BasicDialogListener;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.event.FieldListenerAdapter;

public class WaveInsertPanel implements WaveInsertView {

    public static final String ADD_WAVE = "wip-add-wave";
    public static final String CANCEL = "wip-cancel-add-wave";
    public static final String WAVE_ID = "wip-wave-id-fl";
    private BasicDialogExtended dialog;
    private final WaveInsertPresenter presenter;
    private final I18nTranslationService i18n;
    private FormPanel formPanel;
    private TextField waveId;
    private final ImgResources img;

    public WaveInsertPanel(final WaveInsertPresenter presenter, final I18nTranslationService i18n,
            final ImgResources img) {
        this.presenter = presenter;
        this.i18n = i18n;
        this.img = img;
    }

    public String getWaveId() {
        return waveId.getValueAsString();
    }

    public void hide() {
        dialog.hide();
    }

    public void reset() {
        formPanel.getForm().reset();
    }

    public void show() {
        if (dialog == null) {
            dialog = new BasicDialogExtended(i18n.t("Add a wave"), true, false, 320, 140,
                    ImgConstants.toPath(img.addGreen()), i18n.t("Add"), ADD_WAVE, i18n.t("Cancel"), CANCEL,
                    new BasicDialogListener() {
                        public void onCancelButtonClick() {
                            presenter.onCancel();
                        }

                        public void onFirstButtonClick() {
                            presenter.onAdd();
                        }
                    });
            dialog.setResizable(false);
            createForm();
        }
        dialog.show();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {
            public void execute() {
                waveId.focus();
            }
        });
    }

    private void createForm() {
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setAutoScroll(false);

        formPanel.setWidth(333);
        formPanel.setLabelWidth(100);
        formPanel.setPaddings(10);

        waveId = new TextField(i18n.t("Wave id"), "wave id", 150);
        waveId.setTitle(i18n.t("Something like: [%s]", "wavesandbox.com!w+NdlzA9PU%B"));
        waveId.setAllowBlank(false);
        waveId.setValidationEvent(false);
        // waveId.setRegex("^[a-z0-9_\\-]+$");
        // waveId.setRegexText(i18n.t("Can only contain characters, numbers, and dashes"));
        waveId.setId(WAVE_ID);
        formPanel.add(waveId);

        dialog.add(formPanel);
        waveId.addListener(new FieldListenerAdapter() {
            @Override
            public void onSpecialKey(final Field field, final EventObject e) {
                if (e.getKey() == 13) {
                    presenter.onAdd();
                }
            }
        });
    }
}
