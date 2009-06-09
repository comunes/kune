package org.ourproject.kune.platf.client.ui.rte.saving;
import static org.ourproject.kune.platf.client.ui.rte.basic.AbstractRTEAction.NO_ICON;
import static org.ourproject.kune.platf.client.ui.rte.basic.AbstractRTEAction.NO_TEXT;

import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.BeforeActionListener;
import org.ourproject.kune.platf.client.actions.KeyStroke;
import org.ourproject.kune.platf.client.actions.ui.ButtonDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.shortcuts.Keyboard;
import org.ourproject.kune.platf.client.state.StateManager;
import org.ourproject.kune.platf.client.ui.rte.basic.AbstractRTEAction;
import org.ourproject.kune.platf.client.ui.rte.basic.RTEditorNew;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;
import org.ourproject.kune.platf.client.utils.DeferredCommandWrapper;
import org.ourproject.kune.platf.client.utils.TimerWrapper;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;
import com.google.gwt.libideas.resources.client.ImageResource;

public class RTESavingEditorPresenter implements RTESavingEditor {

        public class AutoSaveAction extends AbstractRTEAction {
            public AutoSaveAction(final String text, final String tooltip, final ImageResource icon) {
                super(text, tooltip, icon);
            }

            public void actionPerformed(final ActionEvent actionEvent) {
                autoSave = !autoSave;
                if (autoSave) {
                    timer.schedule(AUTOSAVE_IN_MILLISECONDS);
                } else {
                    timer.cancel();
                }
            }
              }

    public class CloseAction extends AbstractRTEAction {
    public CloseAction(final String text, final String tooltip, final ImageResource icon) {
        super(text, tooltip, icon);
    }

    public void actionPerformed(final ActionEvent actionEvent) {
        onCancelImpl();
    }
      }
        public class SaveAction extends AbstractRTEAction {
        public SaveAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            onDoSaveImpl();
        }
       }



            public class SaveCloseAction extends AbstractRTEAction {
          public SaveCloseAction(final String text, final String tooltip, final ImageResource icon) {
            super(text, tooltip, icon);
          }

          public void actionPerformed(final ActionEvent actionEvent) {
              if (savePending) {
                  timer.cancel();
                  onDoSaveAndCloseImpl();
              } else {
                  onCancelConfirmedImpl();
              }
          }
       }

        public static final String FILE_DEF_MENU_OPTION = "File";

            public static final int AUTOSAVE_AFTER_FAILS_IN_MILLISECONS = 20000;
            public static final int AUTOSAVE_IN_MILLISECONDS = 10000;
    private final RTEditorNew editor;
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
    private RTESavingEditorView view;

    SaveAction saveAction;
    private SaveAction saveMenuAction;

    public RTESavingEditorPresenter(final RTEditorNew editor, final boolean autoSave, final I18nTranslationService i18n,
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

    public RTEditorNew getBasicEditor() {
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

        saveAction = new SaveAction(NO_TEXT, i18n.t("Save"),imgResources.save());

        final ButtonDescriptor saveBtn = new ButtonDescriptor(saveAction);
        final KeyStroke key_S = KeyStroke.getKeyStroke(Keyboard.KEY_S, Keyboard.MODIFIER_CTRL);
        saveBtn.setPosition(0);
        saveBtn.setLocation(RTEditorNew.SNDBAR);

        saveMenuAction = new SaveAction(i18n.t("Save"),NO_TEXT, imgResources.save());
        final MenuItemDescriptor saveMenu = new MenuItemDescriptor(editor.getFileMenu(), saveMenuAction);
        saveMenu.setPosition(0);
        saveMenu.setLocation(RTEditorNew.TOPBAR);

        editor.setActionShortcut(key_S, saveAction, saveMenuAction);

        final AutoSaveAction autoSaveAction = new AutoSaveAction(i18n.t("Autosave"), NO_TEXT, NO_ICON);
        final MenuCheckItemDescriptor autoSaveItem = new MenuCheckItemDescriptor(editor.getFileMenu(), autoSaveAction) { @Override
        public boolean isChecked() {
            //autoSaveItem.setChecked(autoSave);
            return autoSave;
        }};
        autoSaveItem.setLocation(RTEditorNew.TOPBAR);

        final CloseAction closeAction = new CloseAction(i18n.t("Close"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor closeItem = new MenuItemDescriptor(editor.getFileMenu(), closeAction);
        closeItem.setLocation(RTEditorNew.TOPBAR);

        final SaveCloseAction saveCloseAction = new SaveCloseAction(i18n.t("Save & Close"), NO_TEXT, NO_ICON);
        final MenuItemDescriptor saveClose = new MenuItemDescriptor(editor.getFileMenu(), saveCloseAction);
        final ButtonDescriptor saveCloseBtn = new ButtonDescriptor(saveCloseAction);
        saveClose.setLocation(RTEditorNew.TOPBAR);
        saveCloseBtn.setLocation(RTEditorNew.TOPBAR);

        editor.addAction(saveMenu);
        editor.addAction(saveBtn);
        editor.addAction(autoSaveItem);
        editor.addAction(saveClose);
        editor.addAction(saveCloseBtn);
       //FIXME editor.addAction(new ToolbarSeparatorDescriptor(Type.fill));
        editor.addAction(closeItem);
    }

    private void enableSaveBtn(final boolean enabled) {
        saveAction.setEnabled(enabled);
        saveMenuAction.setEnabled(enabled);
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
            final Listener0 onYes = new Listener0() {
                public void onEvent() {
                    onDoSaveAndCloseImpl();
                }
            };
            final Listener0 onCancel = new Listener0() {
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
