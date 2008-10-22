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

package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PushButton;

public class CustomPushButton extends Composite {

    private final PushButton button;

    public CustomPushButton(final String text, final ClickListener listener) {
        button = new PushButton(text, listener);
        RoundedBorderDecorator roundedButton = new RoundedBorderDecorator(button, RoundedBorderDecorator.ALL,
                RoundedBorderDecorator.SIMPLE);
        initWidget(roundedButton);
        roundedButton.setColor("#AAA");
        button.setStyleName("kune-CustomPushButton");
        this.addStyleName("kune-CustomPushButton-space");
    }

    public boolean isEnabled() {
        return button.isEnabled();
    }

    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
    }

    public void setText(final String text) {
        button.setText(text);
    }

}
