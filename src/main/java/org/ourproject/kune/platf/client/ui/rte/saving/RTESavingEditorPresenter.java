package org.ourproject.kune.platf.client.ui.rte.saving;

import org.ourproject.kune.platf.client.actions.ActionCheckedCondition;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarButtonSeparator;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.BeforeActionListener;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.ShortcutDescriptor;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditor;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class RTESavingEditorPresenter implements RTESavingEditor {

    public static final String FILE_DEF_MENU_OPTION = "File";
    public static final int AUTOSAVE_AFTER_FAILS_IN_MILLISECONS = 20000;
    public static final int AUTOSAVE_IN_MILLISECONDS = 10000;

    private final RTEditor editor;
    private boolean autoSave;
    private boolean savePending;
    private boolean saveAndCloseConfirmed;
    private Listener<String> onSave;
    private Listener0 onEditCancelled;
    private final RTEImgResources imgResources;
    private final TimerWrapper timer;
    private final DeferredCommandWrapper deferredCommandWrapper;
    private final I18nTranslationService i18n;
    private final StateManager stateManager;
    private final BeforeActionListener beforeStateChangeListener;
    ActionToolbarButtonDescriptor<Object> saveBtn;
    private RTESavingEditorView view;

    public RTESavingEditorPresenter(final RTEditor editor, final boolean autoSave, final I18nTranslationService i18n,
            final StateManager stateManager, final DeferredCommandWrapper deferredCommandWrapper,
            final RTEImgResources imgResources, final TimerWrapper timer) {
        this.editor = editor;
        this.autoSave = autoSave;
        this.i18n = i18n;
        this.stateManager = stateManager;
        this.deferredCommandWrapper = deferredCommandWrapper;
        this.imgResources = imgResources;
        this.savePending = false;
        this.saveAndCloseConfirmed = false;
        createActions();
        this.timer =  timer;
        timer.configure(new Listener0() {;

            public void onEvent() {
                onAutoSave();
            }
        });
        editor.addOnEditListener(new Listener0() {
            public void onEvent() {
                onEdit();
            }
        });
        beforeStateChangeListener = new BeforeActionListener() {
            public boolean beforeAction() {
                return beforeTokenChange();
            }
        };
    }

    public void edit(final String html, final Listener<String> onSave, final Listener0 onEditCancelled) {
        this.onSave = onSave;
        this.onEditCancelled = onEditCancelled;
        editor.setHtml(html);
        editor.attach();
        stateManager.addBeforeStateChangeListener(beforeStateChangeListener);
        enableSaveBtn(false);
    }

    public RTEditor getBasicEditor() {
        return editor;
    }

    public BeforeActionListener getBeforeSavingListener() {
        return beforeStateChangeListener;
    }

    public void init(final RTESavingEditorView view) {
        this.view = view;
    }

    public boolean isSavePending() {
        return savePending;
    }

    public void onDoSaveAndClose() {
        onDoSaveAndCloseImpl();
    }

    public void onSavedSuccessful() {
        if (saveAndCloseConfirmed) {
            onCancelConfirmed();
        } else {
            reset();
        }
    }

    public void onSaveFailed() {
        timer.schedule(AUTOSAVE_AFTER_FAILS_IN_MILLISECONS);
        if (saveAndCloseConfirmed) {
            saveAndCloseConfirmed = false;
        }
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

    boolean beforeTokenChange() {
        if (savePending) {
            onCancelImpl();
            return false;
        } else {
            deferredCommandWrapper.addCommand(new Listener0() {
                public void onEvent() {
                    onCancelConfirmed();
                }
            });
            return true;
        }
    }

    void onDoSave() {
        onDoSaveImpl();
    }

    void onEdit() {
        if (!savePending) {
            savePending = true;
            if (autoSave) {
                timer.schedule(AUTOSAVE_IN_MILLISECONDS);
            }
            enableSaveBtn(true);
        }
    }

    private void createActions() {
        Listener0 onPerformSaveCall = new Listener0() {
            public void onEvent() {
                onDoSaveImpl();
            }
        };
        saveBtn = new ActionToolbarButtonDescriptor<Object>(AccessRolDTO.Editor, RTEditor.SNDBAR, onPerformSaveCall);
        saveBtn.setIconCls(RTEImgResources.SUFFIX + imgResources.save().getName());
        saveBtn.setToolTip(i18n.t("Save"));
        ShortcutDescriptor ctrl_S = new ShortcutDescriptor(true, 'S');
        saveBtn.setShortcut(ctrl_S);
        saveBtn.setPosition(0);

        ActionToolbarMenuDescriptor<Object> save = new ActionToolbarMenuDescriptor<Object>(AccessRolDTO.Editor,
                RTEditor.TOPBAR, onPerformSaveCall);
        save.setIconCls(RTEImgResources.SUFFIX + imgResources.save().getName());
        save.setParentMenuTitle(i18n.t(FILE_DEF_MENU_OPTION));
        save.setTextDescription(i18n.t("Save") + (ctrl_S.toString()));
        save.setPosition(0);

        ActionToolbarMenuCheckItemDescriptor<Object> autoSaveMenuItem = new ActionToolbarMenuCheckItemDescriptor<Object>(AccessRolDTO.Editor,
                RTEditor.TOPBAR, new Listener0() {
                    public void onEvent() {
                        autoSave = !autoSave;
                        if (autoSave) {
                            timer.schedule(AUTOSAVE_IN_MILLISECONDS);
                        } else {
                            timer.cancel();
                        }
                    }}
                , new ActionCheckedCondition() {
                    public boolean mustBeChecked() {
                        return autoSave;
                    }});
        autoSaveMenuItem.setParentMenuTitle(i18n.t(FILE_DEF_MENU_OPTION));
        autoSaveMenuItem.setTextDescription(i18n.t("Autosave"));

        ActionToolbarMenuDescriptor<Object> close = new ActionToolbarMenuDescriptor<Object>(AccessRolDTO.Editor,
                RTEditor.TOPBAR, new Listener<Object>() {
                    public void onEvent(final Object parameter) {
                        onCancelImpl();
                    }
                });
        close.setParentMenuTitle(i18n.t(FILE_DEF_MENU_OPTION));
        close.setTextDescription(i18n.t("Close"));

        Listener0 onPerformSaveAndCloseCall = new Listener0() {
            public void onEvent() {
                if (savePending) {
                    timer.cancel();
                    onDoSaveAndCloseImpl();
                } else {
                    onCancelConfirmedImpl();
                }
            }
        };
        ActionToolbarMenuDescriptor<Object> saveclose = new ActionToolbarMenuDescriptor<Object>(AccessRolDTO.Editor,
                RTEditor.TOPBAR, onPerformSaveAndCloseCall);
        saveclose.setParentMenuTitle(i18n.t(FILE_DEF_MENU_OPTION));
        saveclose.setTextDescription(i18n.t("Save & Close"));

        ActionToolbarButtonDescriptor<Object> saveCloseBtn = new ActionToolbarButtonDescriptor<Object>(
                AccessRolDTO.Editor, RTEditor.TOPBAR, onPerformSaveAndCloseCall);
        // saveCloseBtn.setIconCls(RTEImgResources.SUFFIX +
        // imgResources.save().getName());
        saveCloseBtn.setTextDescription(i18n.t("Save & Close"));
        saveCloseBtn.setLeftSeparator(ActionToolbarButtonSeparator.fill);

        editor.addAction(save);
        editor.addAction(autoSaveMenuItem);
        editor.addAction(saveclose);
        editor.addAction(close);

        editor.addAction(saveBtn);
        editor.addAction(saveCloseBtn);
    }

    private void enableSaveBtn(final boolean enable) {
        editor.getSndBar().setButtonEnable(saveBtn, enable);
    }

    private void onCancelConfirmedImpl() {
        stateManager.removeBeforeStateChangeListener(beforeStateChangeListener);
        stateManager.resumeTokenChange();
        reset();
        editor.detach();
        onDoEditCancelled();
    }

    private void onCancelImpl() {
        if (savePending) {
            timer.cancel();
            Listener0 onYes = new Listener0() {
                public void onEvent() {
                    onDoSaveAndCloseImpl();
                }
            };
            Listener0 onCancel = new Listener0() {
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

    private void onDoSaveAndCloseImpl() {
        saveAndCloseConfirmed = true;
        onDoSaveImpl();
    }

    private void onDoSaveImpl() {
        onSave.onEvent(editor.getHtml());
    }

    private void reset() {
        timer.cancel();
        savePending = false;
        saveAndCloseConfirmed = false;
        enableSaveBtn(false);
        editor.reset();
    }
}
