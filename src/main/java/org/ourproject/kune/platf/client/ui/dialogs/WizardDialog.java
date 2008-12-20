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
import org.ourproject.kune.workspace.client.site.Site;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Component;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Toolbar;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.event.WindowListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;

public class WizardDialog implements WizardDialogView {

    private final Button backButton;
    private final Button cancelButton;
    private final BasicDialog dialog;
    private final Button finishButton;
    private final I18nTranslationService i18n;
    private final Button nextButton;
    private final DeckPanel deck;
    private final Panel mainPanel;

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
        mainPanel = new Panel();
        mainPanel.setLayout(new FitLayout());
        deck = new DeckPanel();
        mainPanel.add(deck);
    }

    public WizardDialog(String dialogId, final String caption, final boolean modal, final boolean minimizable,
            final int width, final int height, final WizardListener listener, final I18nTranslationService i18n,
            final String backId, final String nextId, final String finishId, final String cancelId, final String closeId) {
        this(dialogId, caption, modal, minimizable, width, height, width, height, backId, nextId, finishId, cancelId,
                closeId, i18n, listener);
    }

    public void add(View view) {
        deck.add(toWidget(view));
        doLayoutIfNeeded();
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

    public boolean isCurrentPage(View view) {
        int visibleWidgetIndex = deck.getVisibleWidget();
        if (visibleWidgetIndex == -1) {
            return false;
        } else {
            return deck.getWidget(visibleWidgetIndex).equals(toWidget(view));
        }
    }

    public void mask(final String message) {
        dialog.getEl().mask(message, "x-mask-loading");
    }

    public void maskProcessing() {
        mask(i18n.t("Processing"));
    }

    public void remove(View view) {
        int count = getWidgetCount(view);
        if (count != -1) {
            deck.remove(count);
        }
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

    public void show(View view) {
        int count = getWidgetCount(view);
        if (count != -1) {
            deck.showWidget(count);
            dialog.add(mainPanel);
            doLayoutIfNeeded();
        } else {
            Site.error("Widget not found in deck of WizardDialog");
        }

    }

    public void unMask() {
        dialog.getEl().unmask();
    }

    private void doLayoutIfNeeded() {
        if (dialog.isRendered()) {
            dialog.doLayout();
            mainPanel.syncSize();
            mainPanel.doLayout();
        }
    }

    private int getWidgetCount(View view) {
        if (view instanceof Widget) {
            return deck.getWidgetIndex((Widget) view);
        } else if (view instanceof DefaultForm) {
            return deck.getWidgetIndex(((DefaultForm) view).getFormPanel());
        }
        return -1;
    }

    private Widget toWidget(View view) {
        if (view instanceof Widget) {
            return (Widget) view;
        } else if (view instanceof DefaultForm) {
            return ((DefaultForm) view).getFormPanel();
        } else {
            Site.error("Trying to add a unknown element in WizardDialog");
            return null;
        }
    }
}
