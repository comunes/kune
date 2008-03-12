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

package org.ourproject.kune.platf.client.ui.dialogs;

import org.ourproject.kune.platf.client.services.Kune;
import org.ourproject.kune.platf.client.ui.CustomButton;
import org.ourproject.kune.workspace.client.ui.form.WizardListener;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.WindowListenerAdapter;

public class WizardDialog {

    private final BasicDialog dialog;
    private final Button backButton;
    private final Button nextButton;
    private final Button cancelButton;
    private final Button finishButton;

    public WizardDialog(final String caption, final boolean modal, final boolean minimizable, final int width,
            final int height, final int minWidth, final int minHeight, final WizardListener listener) {
        dialog = new BasicDialog(caption, modal, false, width, height, minWidth, minHeight);
        dialog.setCollapsible(minimizable);
        dialog.setShadow(true);
        dialog.setPlain(true);
        dialog.setCollapsible(false);
        dialog.setResizable(false);

        backButton = new CustomButton(Kune.I18N.tWithNT("« Back", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onBack();
            }
        }).getButton();
        dialog.addButton(backButton);

        nextButton = new CustomButton(Kune.I18N.tWithNT("Next »", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onNext();
            }
        }).getButton();
        dialog.addButton(nextButton);

        cancelButton = new CustomButton(Kune.I18N.tWithNT("Cancel", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onCancel();
            }
        }).getButton();
        dialog.addButton(cancelButton);

        finishButton = new CustomButton(Kune.I18N.tWithNT("Finish", "used in button"), new ClickListener() {
            public void onClick(final Widget sender) {
                listener.onFinish();
            }
        }).getButton();
        dialog.addButton(finishButton);

        dialog.addListener(new WindowListenerAdapter() {
            public void onClose(final Panel panel) {
                listener.onClose();
            }
        });
    }

    public WizardDialog(final String caption, final boolean modal, final boolean minimizable, final int width,
            final int height, final WizardListener listener) {
        this(caption, modal, minimizable, width, height, width, height, listener);
    }

    public void add(final Widget widget) {
        dialog.add(widget);
    }

    public void show() {
        dialog.show();
    }

    public void center() {
        dialog.center();
    }

    public void hide() {
        dialog.hide();
    }

    public void setVisibleNextButton(final boolean visible) {
        nextButton.setVisible(visible);
    }

    public void setVisibleBackButton(final boolean visible) {
        backButton.setVisible(visible);
    }

    public void setVisibleFinishButton(final boolean visible) {
        finishButton.setVisible(visible);
    }

    public void setVisibleCancelButton(final boolean visible) {
        cancelButton.setVisible(visible);
    }

    public void setEnabledNextButton(final boolean enabled) {
        if (enabled) {
            nextButton.enable();
        } else {
            nextButton.disable();
        }
    }

    public void setEnabledBackButton(final boolean enabled) {
        if (enabled) {
            backButton.enable();
        } else {
            backButton.disable();
        }
    }

    public void setEnabledFinishButton(final boolean enabled) {
        if (enabled) {
            finishButton.enable();
        } else {
            finishButton.disable();
        }
    }

    public void setEnabledCancelButton(final boolean enabled) {
        if (enabled) {
            cancelButton.enable();
        } else {
            cancelButton.disable();
        }
    }

    public void setFinishText(final String text) {
        finishButton.setText(text);
    }

    public void mask(final String message) {
        dialog.getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(Kune.I18N.t("Processing"));
    }

    public void unMask() {
        dialog.getEl().unmask();
    }

}
