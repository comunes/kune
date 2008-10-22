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
package org.ourproject.kune.workspace.client.site.msg;

import org.ourproject.kune.platf.client.services.Images;
import org.ourproject.kune.platf.client.ui.KuneUiUtils;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.core.ExtElement;

public class SiteMessagePanel extends SimpleMessagePanel implements SiteMessageView {
    private static final int TIMEVISIBLE = 4000;

    private final Timer timer;

    private final ExtElement extElem;

    public SiteMessagePanel(final MessagePresenter presenter, final boolean closable,
            final I18nUITranslationService i18n) {
        final Images images = Images.App.getInstance();
        timer = new Timer() {
            public void run() {
                hide();
                if (presenter != null) {
                    presenter.resetMessage();
                }
            }
        };

        if (closable) {
            final PushButton closeIcon = new PushButton(images.cross().createImage(), images.crossDark().createImage());
            KuneUiUtils.setQuickTip(closeIcon, i18n.t("Click to close"));
            add(closeIcon);
            closeIcon.addClickListener(new ClickListener() {
                public void onClick(final Widget sender) {
                    if (sender == closeIcon) {
                        setVisible(false);
                        hide();
                        if (presenter != null) {
                            presenter.resetMessage();
                        }
                    }
                }
            });
            setCellVerticalAlignment(closeIcon, VerticalPanel.ALIGN_BOTTOM);
        }
        extElem = new ExtElement(this.getElement());
        final int clientWidth = Window.getClientWidth();
        adjustWidth(clientWidth);
        Window.addWindowResizeListener(new WindowResizeListener() {
            public void onWindowResized(final int width, final int height) {
                // Log.debug("Window width: " + width + ", height: " + height);
                setXY(width);
                adjustWidth(width);
            }
        });
        super.reset();
        super.show();
        extElem.setVisible(false, false);
        RootPanel.get().add(this, calculateX(clientWidth), calculateY());
    }

    @Override
    public void adjustWidth(final int windowWidth) {
        super.adjustWidth(windowWidth);
    }

    public void hide() {
        extElem.hide(true);
        super.reset();
        timer.cancel();
    }

    public void show() {
        extElem.show(false);
        // super.show();
        timer.schedule(TIMEVISIBLE);
    }

    private int calculateX(final int clientWidth) {
        final int x = clientWidth * 20 / 100 - 10;
        // Log.debug("X: " + x);
        return x;
    }

    private int calculateY() {
        return 2;
    }

    private void setXY(final int clientWidth) {
        extElem.setXY(calculateX(clientWidth), calculateY(), false);
    }

}
