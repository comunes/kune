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
package org.ourproject.kune.workspace.client.sitebar.msg;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class SiteMessagePanel extends SimpleMessagePanel implements SiteMessageView {
    private static final int TIMEVISIBLE = 4000;

    private final Timer timer;

    public SiteMessagePanel(final MessagePresenter presenter, final boolean closable) {
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
    }

    public void hide() {
	super.hide();
	timer.cancel();
    }

    public void show() {
	super.show();
	timer.schedule(TIMEVISIBLE);
    }
}
