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
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.LayoutDialog;
import com.gwtext.client.widgets.LayoutDialogConfig;
import com.gwtext.client.widgets.event.DialogListener;
import com.gwtext.client.widgets.layout.BorderLayout;
import com.gwtext.client.widgets.layout.ContentPanel;
import com.gwtext.client.widgets.layout.LayoutRegionConfig;

public class WizardDialog {

    private final LayoutDialog dialog;
    private final Button backButton;
    private final Button nextButton;
    private final Button cancelButton;
    private final Button finishButton;

    public WizardDialog(final String caption, final boolean modal, final boolean minimizable, final int width,
            final int height, final int minWidth, final int minHeight, final WizardListener listener) {
        dialog = new LayoutDialog(new LayoutDialogConfig() {
            {
                // Param values
                setTitle(caption);
                setModal(modal);
                setWidth(width);
                setHeight(height);
                setMinWidth(minWidth);
                setMinHeight(minHeight);
                setCollapsible(minimizable);

                // Def values
                setShadow(true);
            }
        }, new LayoutRegionConfig());

        backButton = dialog.addButton(new CustomButton(Kune.I18N.tWithNT("« Back", "used in button"),
                new ClickListener() {
                    public void onClick(final Widget sender) {
                        listener.onBack();
                    }
                }).getButton());

        nextButton = dialog.addButton(new CustomButton(Kune.I18N.tWithNT("Next »", "used in button"),
                new ClickListener() {
                    public void onClick(final Widget sender) {
                        listener.onNext();
                    }
                }).getButton());

        cancelButton = dialog.addButton(new CustomButton(Kune.I18N.tWithNT("Cancel", "used in button"),
                new ClickListener() {
                    public void onClick(final Widget sender) {
                        listener.onCancel();
                    }
                }).getButton());

        finishButton = dialog.addButton(new CustomButton(Kune.I18N.tWithNT("Finish", "used in button"),
                new ClickListener() {
                    public void onClick(final Widget sender) {
                        listener.onFinish();
                    }
                }).getButton());

        dialog.addDialogListener(new DialogListener() {

            public boolean doBeforeHide(final LayoutDialog dialog) {
                listener.onClose();
                return true;
            }

            public boolean doBeforeShow(final LayoutDialog dialog) {
                return true;
            }

            public void onHide(final LayoutDialog dialog) {
            }

            public void onKeyDown(final LayoutDialog dialog, final EventObject e) {
            }

            public void onMove(final LayoutDialog dialog, final int x, final int y) {
            }

            public void onResize(final LayoutDialog dialog, final int width, final int height) {
            }

            public void onShow(final LayoutDialog dialog) {
            }
        });

    }

    public WizardDialog(final String caption, final boolean modal, final boolean minimizable, final int width,
            final int height, final WizardListener listener) {
        this(caption, modal, minimizable, width, height, width, height, listener);
    }

    public void add(final Widget widget) {
        BorderLayout layout = dialog.getLayout();
        ContentPanel contentPanel = new ContentPanel();
        contentPanel.add(widget);
        layout.add(contentPanel);
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
