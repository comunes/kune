package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractGuiItem extends Composite implements View {

    protected Action action;

    /**
     * Listener the button uses to receive PropertyChangeEvents from its Action.
     */
    protected PropertyChangeListener changeListener;

    public void setAction(final Action newaction) {
        if (action != null) {
            action.removePropertyChangeListener(changeListener);
            // removeActionListener(action);
            if (changeListener != null) {
                action.removePropertyChangeListener(changeListener);
                // @PMD:REVIEWED:NullAssignment: by vjrj on 24/05/09 20:05
                changeListener = null;
            }
        }

        action = newaction;
        configurePropertiesFromAction(action);
        if (action != null) {
            changeListener = createActionPropertyChangeListener(newaction);
            action.addPropertyChangeListener(changeListener);
            // addActionListener(action);
        }
    }

    protected void configurePropertiesFromAction(final Action action) {
        configurePropertiesFromActionImpl(action);
    }

    protected PropertyChangeListener createActionPropertyChangeListener(final Action action) {
        return new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                final Action act = (Action) (event.getSource());
                if (event.getPropertyName().equals(Action.ENABLED)) {
                    setEnabled(act.isEnabled());
                } else if (event.getPropertyName().equals(Action.NAME)) {
                    setText((String) (act.getValue(Action.NAME)));
                } else if (event.getPropertyName().equals(Action.SMALL_ICON)) {
                    setIcon((ImageResource) (act.getValue(Action.SMALL_ICON)));
                } else if (event.getPropertyName().equals(Action.SHORT_DESCRIPTION)) {
                    setToolTipText((String) (act.getValue(Action.SHORT_DESCRIPTION)));
                }
                // else if (e.getPropertyName().equals(Action.MNEMONIC_KEY)) {
                // if (act.getValue(Action.MNEMONIC_KEY) != null) {
                // setMnemonic(((Integer) (act.getValue(Action.MNEMONIC_KEY)))
                // .intValue());
                // } else if
                // (e.getPropertyName().equals(Action.ACTION_COMMAND_KEY)) {
                // setActionCommand((String)
                // (act.getValue(Action.ACTION_COMMAND_KEY)));
                // }
                // }
            }
        };
    }

    protected abstract void setEnabled(boolean enabled);

    protected abstract void setIcon(ImageResource imageResource);

    protected abstract void setText(String text);

    protected abstract void setToolTipText(String text);

    private void configurePropertiesFromActionImpl(final Action action) {
        if (action == null) {
            setText(null);
            setIcon(null);
            setEnabled(true);
            setToolTipText(null);
        } else {
            setText((String) (action.getValue(Action.NAME)));
            setIcon((ImageResource) (action.getValue(Action.SMALL_ICON)));
            setEnabled(action.isEnabled());
            setToolTipText((String) (action.getValue(Action.SHORT_DESCRIPTION)));
            // if (a.getValue(Action.MNEMONIC_KEY) != null) {
            // setMnemonic(((Integer)
            // (a.getValue(Action.MNEMONIC_KEY))).intValue());
            // }
            // String actionCommand = (String)
            // (a.getValue(Action.ACTION_COMMAND_KEY));
            //
            // // Set actionCommand to button's text by default if it is not
            // // specified
            // if (actionCommand != null) {
            // setActionCommand((String)
            // (a.getValue(Action.ACTION_COMMAND_KEY)));
            // } else {
            // setActionCommand(getText());
            // }
        }
    }

}
