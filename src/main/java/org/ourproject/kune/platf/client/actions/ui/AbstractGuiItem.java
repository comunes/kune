/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

import cc.kune.core.client.resources.icons.IconConstants;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractGuiItem extends Composite implements View {

    protected final GuiActionDescrip descriptor;

    public AbstractGuiItem(final GuiActionDescrip descriptor) {
        super();
        this.descriptor = descriptor;
    }

    /**
     * Sets the item properties from the stored values
     */
    public void configureItemFromProperties() {
        configure();
    }

    public AbstractAction getAction() {
        return descriptor.getAction();
    }

    protected abstract void setEnabled(boolean enabled);

    protected abstract void setIconStyle(String style);

    protected abstract void setIconUrl(String iconUrl);

    protected abstract void setText(String text);

    protected abstract void setToolTipText(String text);

    private void configure() {
        configureProperties();
        final PropertyChangeListener changeListener = createActionPropertyChangeListener();
        descriptor.getAction().addPropertyChangeListener(changeListener);
        descriptor.addPropertyChangeListener(changeListener);
    }

    private void configureProperties() {
        setText((String) (descriptor.getValue(Action.NAME)));
        setIcon(descriptor.getValue(Action.SMALL_ICON));
        setEnabled((Boolean) descriptor.getValue(AbstractAction.ENABLED));
        setToolTipText((String) (descriptor.getValue(Action.SHORT_DESCRIPTION)));
        setVisible((Boolean) descriptor.getValue(GuiActionDescrip.VISIBLE));
    }

    private PropertyChangeListener createActionPropertyChangeListener() {
        return new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                final Object newValue = event.getNewValue();
                if (event.getPropertyName().equals(Action.ENABLED)) {
                    setEnabled((Boolean) newValue);
                } else if (event.getPropertyName().equals(Action.NAME)) {
                    setText((String) newValue);
                } else if (event.getPropertyName().equals(Action.SMALL_ICON)) {
                    setIcon(newValue);
                } else if (event.getPropertyName().equals(Action.SHORT_DESCRIPTION)) {
                    setToolTipText((String) newValue);
                } else if (event.getPropertyName().equals(GuiActionDescrip.VISIBLE)) {
                    setVisible((Boolean) newValue);
                }
            }
        };
    }

    private void setEnabled(final Boolean enabled) {
        setEnabled(enabled == null ? true : enabled);
    }

    private void setIcon(final Object icon) {
        if (icon instanceof CssStyleDescriptor) {
            setIconStyle(((CssStyleDescriptor) icon).getName());
        } else if (icon instanceof ImageResource) {
            setIconStyle((IconConstants.CSS_SUFFIX + ((ImageResource) icon).getName()));
        } else if (icon instanceof String) {
            setIconUrl((String) icon);
        }
    }

    private void setVisible(final Boolean visible) {
        setVisible(visible == null ? true : visible);
    }
}
