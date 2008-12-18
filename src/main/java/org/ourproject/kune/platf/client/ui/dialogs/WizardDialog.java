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
package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.services.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public class WizardDialog implements WizardDialogView {

    private final Button backButton;
    private final Button cancelButton;
    private final BasicDialog dialog;
    private final Button finishButton;
    private final I18nTranslationService i18n;
    private final Button nextButton;

    public WizardDialog(String dialogId, final String caption, final boolean modal, final boolean minimizable,
            final int width, final int height, final int minWidth, final int minHeight, final String backId,
            final String nextId, final String finishId, final String cancelId, final String closeId,
            final I18nTranslationService i18n, final WizardListener listener) {
        dialog = new BasicDialog(dialogId, caption, modal, false, width, height, minWidth, minHeight);
        this.i18n = i18n;
        dialog.setCollapsible(minimizable);
        dialog.setShadow(true);
        dialog.setPlain(true);
        dialog.setCollapsible(false);
        dialog.setResizable(false);
        dialog.setId(dialogId);

        backButton = new Button(i18n.tWithNT("« Back", "used in button"), new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                listener.onBack();
            }
        });
        backButton.setId(backId);
        dialog.addButton(backButton);

        nextButton = new Button(i18n.tWithNT("Next »", "used in button"), new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                listener.onNext();
            }
        });
        nextButton.setId(nextId);
        dialog.addButton(nextButton);

        cancelButton = new Button(i18n.tWithNT("Cancel", "used in button"), new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                listener.onCancel();
            }
        });
        cancelButton.setId(cancelId);
        dialog.addButton(cancelButton);

        finishButton = new Button(i18n.tWithNT("Finish", "used in button"), new ButtonListenerAdapter() {
            @Override
            public void onClick(Button button, EventObject e) {
                listener.onFinish();
            }
        });
        finishButton.setId(finishId);
        dialog.addButton(finishButton);

        dialog.addListener(new WindowListenerAdapter() {
            @Override
            public void onHide(Component component) {
                listener.onClose();
            }
        });
    }

    public WizardDialog(String dialogId, final String caption, final boolean modal, final boolean minimizable,
            final int width, final int height, final WizardListener listener, final I18nTranslationService i18n,
            final String backId, final String nextId, final String finishId, final String cancelId, final String closeId) {
        this(dialogId, caption, modal, minimizable, width, height, width, height, backId, nextId, finishId, cancelId,
                closeId, i18n, listener);
    }

    public void add(View view) {
        if (view instanceof Widget) {
            dialog.add((Widget) view);
        } else if (view instanceof Panel) {
            dialog.add((Panel) view);
        } else if (view instanceof DefaultForm) {
            dialog.add(((DefaultForm) view).getFormPanel());
        } else {
            Log.error("Trying to add a unknown element in WizardDialog");
        }
        doLayoutIfNeeded();
    }

    @Deprecated
    public void add(final Widget widget) {
        dialog.add(widget);
    }

    public void center() {
        dialog.center();
    }

    public void clear() {
        dialog.clear();
    }

    public void hide() {
        dialog.hide();
    }

    public void mask(final String message) {
        dialog.getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void remove(View view) {
        dialog.remove((Widget) view);
    }

    public void setBottomToolbar(Toolbar toolbar) {
        dialog.setBottomToolbar(toolbar);
    }

    public void setEnabled(boolean back, boolean next, boolean cancel, boolean finish) {
        setEnabledBackButton(back);
        setEnabledNextButton(next);
        setEnabledCancelButton(cancel);
        setEnabledFinishButton(finish);
    }

    public void setEnabledBackButton(final boolean enabled) {
        if (enabled) {
            backButton.enable();
        } else {
            backButton.disable();
        }
    }

    public void setEnabledCancelButton(final boolean enabled) {
        if (enabled) {
            cancelButton.enable();
        } else {
            cancelButton.disable();
        }
    }

    public void setEnabledFinishButton(final boolean enabled) {
        if (enabled) {
            finishButton.enable();
        } else {
            finishButton.disable();
        }
    }

    public void setEnabledNextButton(final boolean enabled) {
        if (enabled) {
            nextButton.enable();
        } else {
            nextButton.disable();
        }
    }

    public void setFinishText(final String text) {
        finishButton.setText(text);
    }

    public void setIconCls(String iconCls) {
        dialog.setIconCls(iconCls);
    }

    public void setVisible(boolean back, boolean next, boolean cancel, boolean finish) {
        setVisibleBackButton(back);
        setVisibleNextButton(next);
        setVisibleCancelButton(cancel);
        setVisibleFinishButton(finish);
    }

    public void setVisibleBackButton(final boolean visible) {
        backButton.setVisible(visible);
    }

    public void setVisibleCancelButton(final boolean visible) {
        cancelButton.setVisible(visible);
    }

    public void setVisibleFinishButton(final boolean visible) {
        finishButton.setVisible(visible);
    }

    public void setVisibleNextButton(final boolean visible) {
        nextButton.setVisible(visible);
    }

    public void show() {
        dialog.show();
    }

    public void unMask() {
        dialog.getEl().unmask();
    }

    private void doLayoutIfNeeded() {
        if (dialog.isRendered()) {
            dialog.doLayout();
        }
    }
}
