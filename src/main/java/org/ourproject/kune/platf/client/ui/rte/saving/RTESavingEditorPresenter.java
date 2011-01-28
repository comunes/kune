package org.ourproject.kune.platf.client.ui.rte.saving;

import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_ICON;
import static org.ourproject.kune.platf.client.actions.AbstractExtendedAction.NO_TEXT;

import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorPresenter;
import org.ourproject.kune.platf.client.ui.rte.edithtml.EditHtmlDialog;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;
import org.ourproject.kune.platf.client.ui.rte.insertlink.InsertLinkDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.inserttable.InsertTableDialog;

import cc.kune.common.client.actions.BeforeActionListener;
import cc.kune.common.client.utils.SchedulerManager;
import cc.kune.common.client.utils.TimerWrapper;
import cc.kune.common.client.utils.TimerWrapper.Executer;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.calclab.suco.client.ioc.Provider;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.ImageResource;


public class RTESavingEditorPresenter extends RTEditorPresenter implements RTESavingEditor {

    public class AutoSaveAction extends AbstractExtendedAction {
        public AutoSaveAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            // bitwise, similar to: autoSave = !autoSave; but fast
            autoSave ^= true;
            if (autoSave) {
                timer.schedule(AUTOSAVE_IN_MILLIS);
            } else {
                timer.cancel();
            }
        }
    }

    public class CloseAction extends AbstractExtendedAction {
        public CloseAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            onCancelImpl();
        }
    }
    public class SaveAction extends AbstractExtendedAction {
        public SaveAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            onDoSaveImpl();
        }
    }

    public class SaveCloseAction extends AbstractExtendedAction {
        public SaveCloseAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            if (savePending) {
                timer.cancel();
                onDoSaveAndCloseImpl();
            } else {
                onCancelConfirmedImpl();
            }
        }
    }

    public static final int AUTOSAVE_AFTER_FAIL_MILLS = 20000;
    public static final int AUTOSAVE_IN_MILLIS = 10000;
    private boolean autoSave;
    private final BeforeActionListener beforeStateChg;
    private final SchedulerManager deferred;
    private final I18nTranslationService i18n;
    private final RTEImgResources imgResources;
    private Listener0 onEditCancelled;
    private Listener<String> onSave;
    SaveAction saveAction;
    private boolean saveCloseConfirmed;
    private SaveAction saveMenuAction;
    private boolean savePending;
    private final StateManager stateManager;

    private final TimerWrapper timer;
    private RTESavingEditorView view;

    public RTESavingEditorPresenter(final I18nTranslationService i18n, final Session session,
            final RTEImgResources imgResources, final Provider<InsertLinkDialog> insLinkDialog,
            final Provider<ColorWebSafePalette> palette, final Provider<EditHtmlDialog> editHtmlDialog,
            final Provider<InsertImageDialog> insertImageDialog, final Provider<InsertMediaDialog> insertMediaDialog,
            final Provider<InsertTableDialog> insertTableDialog, final Provider<InsertSpecialCharDialog> insCharDialog,
            final SchedulerManager deferred, final boolean autoSave,
            final StateManager stateManager,             final TimerWrapper timer) {
        super(i18n, session, imgResources, insLinkDialog, palette, editHtmlDialog, insertImageDialog, insertMediaDialog, insertTableDialog, insCharDialog, deferred);
        this.autoSave = autoSave;
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.deferred = deferred;
        this.imgResources = imgResources;
        this.savePending = false;
        this.saveCloseConfirmed = false;
        this.timer =  timer;
        timer.configure(new Executer() {;

        @Override
        public void execute() {
            onAutoSave();
        }
        });

        super.addOnEditListener(new Listener0() {
            @Override
            public void onEvent() {
                onEdit();
            }
        });
        beforeStateChg = new BeforeActionListener() {
            @Override
            public boolean beforeAction() {
                return beforeTokenChange();
            }
        };
    }

    boolean beforeTokenChange() {
        boolean result = false;
        if (savePending) {
            onCancelImpl();
        } else {
            deferred.addCommand(new ScheduledCommand() {
                @Override
                public void execute() {
                    onCancelConfirmed();
                }
            });
            result = true;
        }
        return result;
    }

    private void createActions() {
        saveAction = new SaveAction(NO_TEXT, i18n.t("Save"),imgResources.save());

        final ButtonDescriptor saveBtn = new ButtonDescriptor(saveAction);
        final KeyStroke key_S = KeyStroke.getKeyStroke(Character.valueOf('S'), Keyboard.MODIFIER_CTRL);
        saveBtn.setPosition(0);
        saveBtn.setLocation(RTEditor.SNDBAR);

        saveMenuAction = new SaveAction(i18n.t("Save"),NO_TEXT, imgResources.save());
        final MenuItemDescriptor saveMenu = new MenuItemDescriptor(super.getFileMenu(), saveMenuAction);
        saveMenu.setPosition(0);
        saveMenu.setLocation(RTEditor.TOPBAR);

        super.setActionShortcut(key_S, saveAction, saveMenuAction);

        final AutoSaveAction autoSaveAction = new AutoSaveAction(i18n.t("Autosave"), NO_TEXT, NO_ICON);
        final MenuCheckItemDescriptor autoSaveItem = new MenuCheckItemDescriptor(super.getFileMenu(), autoSaveAction) { @Override
            public boolean isChecked() {
            //autoSaveItem.setChecked(autoSave);
            return autoSave;
        }};
        autoSaveItem.setLocation(RTEditor.TOPBAR);

        final CloseAction closeAction = new CloseAction(i18n.t("Close"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor closeItem = new MenuItemDescriptor(super.getFileMenu(), closeAction);
        closeItem.setLocation(RTEditor.TOPBAR);

        final SaveCloseAction saveCloseAction = new SaveCloseAction(i18n.t("Save & Close"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor saveClose = new MenuItemDescriptor(super.getFileMenu(), saveCloseAction);
        final ButtonDescriptor saveCloseBtn = new ButtonDescriptor(saveCloseAction);
        saveClose.setLocation(RTEditor.TOPBAR);
        saveCloseBtn.setLocation(RTEditor.TOPBAR);

        addAction(saveMenu);
        addAction(saveBtn);
        addAction(autoSaveItem);
        addAction(saveClose);
        addAction(saveCloseBtn);
        addAction(closeItem);
    }

    @Override
    public void edit(final String html, final Listener<String> onSave, final Listener0 onEditCancelled) {
        this.onSave = onSave;
        this.onEditCancelled = onEditCancelled;
        super.setHtml(html);
        super.attach();
        stateManager.addBeforeStateChangeListener(beforeStateChg);
        enableSaveBtn(false);
    }

    private void enableSaveBtn(final boolean enabled) {
        saveAction.setEnabled(enabled);
        saveMenuAction.setEnabled(enabled);
    }

    @Override
    public BeforeActionListener getBeforeSavingListener() {
        return beforeStateChg;
    }

    public void init(final RTESavingEditorView view) {
        super.init(view);
        this.view = view;
        createActions();
    }

    @Override
    public boolean isSavePending() {
        return savePending;
    }

    protected void onAutoSave() {
        onDoSave();
    }

    protected void onCancel() {
        onCancelImpl();
    }

    protected void onCancelConfirmed() {
        onCancelConfirmedImpl();
    }

    private void onCancelConfirmedImpl() {
        stateManager.removeBeforeStateChangeListener(beforeStateChg);
        stateManager.resumeTokenChange();
        reset();
        super.detach();
        onDoEditCancelled();
    }

    private void onCancelImpl() {
        if (savePending) {
            timer.cancel();
            final Listener0 onYes = new Listener0() {
                @Override
                public void onEvent() {
                    onDoSaveAndCloseImpl();
                }
            };
            final Listener0 onCancel = new Listener0() {
                @Override
                public void onEvent() {
                    onCancelConfirmedImpl();
                }
            };
            view.askConfirmation(i18n.t("Save confirmation"),
                    i18n.t("Do you want to save before closing the editor?"), onYes, onCancel);
        } else {
            onCancelConfirmedImpl();
        }
    }

    private void onDoEditCancelled() {
        onEditCancelled.onEvent();
    }

    void onDoSave() {
        onDoSaveImpl();
    }

    public void onDoSaveAndClose() {
        onDoSaveAndCloseImpl();
    }

    private void onDoSaveAndCloseImpl() {
        saveCloseConfirmed = true;
        onDoSaveImpl();
    }

    private void onDoSaveImpl() {
        onSave.onEvent(super.getHtml());
    }

    void onEdit() {
        if (!savePending) {
            savePending = true;
            if (autoSave) {
                timer.schedule(AUTOSAVE_IN_MILLIS);
            }
            enableSaveBtn(true);
        }
    }

    @Override
    public void onSavedSuccessful() {
        if (saveCloseConfirmed) {
            onCancelConfirmed();
        } else {
            reset();
        }
    }

    @Override
    public void onSaveFailed() {
        timer.schedule(AUTOSAVE_AFTER_FAIL_MILLS);
        if (saveCloseConfirmed) {
            saveCloseConfirmed = false;
        }
    }

    @Override
    public void reset() {
        timer.cancel();
        savePending = false;
        saveCloseConfirmed = false;
        enableSaveBtn(false);
        super.reset();
    }
}
