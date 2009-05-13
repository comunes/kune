package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Composite;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;

public abstract class AbstractButton extends Composite {

    private Action action;
    private final Button button;

    /**
     * Listener the button uses to receive PropertyChangeEvents from its Action.
     */
    PropertyChangeListener actionPropertyChangeListener;

    public AbstractButton() {
        button = new Button();
        initWidget(button);
        button.addListener(new ButtonListenerAdapter() {
            @Override
            public void onClick(final Button button, final EventObject e) {
                if (action != null) {
                    action.actionPerformed(new ActionEvent(button, e.getBrowserEvent()));
                }
            }
        });
    }

    public void setAction(final Action a) {
        if (action != null) {
            action.removePropertyChangeListener(actionPropertyChangeListener);
            // removeActionListener(action);
            if (actionPropertyChangeListener != null) {
                action.removePropertyChangeListener(actionPropertyChangeListener);
                actionPropertyChangeListener = null;
            }
        }

        action = a;
        configurePropertiesFromAction(action);
        if (action != null) {
            actionPropertyChangeListener = createActionPropertyChangeListener(a);
            action.addPropertyChangeListener(actionPropertyChangeListener);
            // addActionListener(action);
        }
    }

    public void setEnabled(final boolean enabled) {
        if (enabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    public void setIcon(final ImageResource imageResource) {
        if (imageResource != null) {
            // FIXME
            button.setIconCls(RTEImgResources.SUFFIX + imageResource.getName());
        }
    }

    public void setText(final String text) {
        button.setText(text);
    }

    public void setToolTipText(final String tooltip) {
        button.setTooltip(tooltip);
    }

    protected void configurePropertiesFromAction(final Action a) {
        if (a == null) {
            setText(null);
            setIcon(null);
            setEnabled(true);
            setToolTipText(null);
        } else {
            setText((String) (a.getValue(Action.NAME)));
            setIcon((ImageResource) (a.getValue(Action.SMALL_ICON)));
            setEnabled(a.isEnabled());
            setToolTipText((String) (a.getValue(Action.SHORT_DESCRIPTION)));
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

    protected PropertyChangeListener createActionPropertyChangeListener(final Action a) {
        return new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent e) {
                Action act = (Action) (e.getSource());
                if (e.getPropertyName().equals(Action.ENABLED)) {
                    setEnabled(act.isEnabled());
                } else if (e.getPropertyName().equals(Action.NAME)) {
                    setText((String) (act.getValue(Action.NAME)));
                } else if (e.getPropertyName().equals(Action.SMALL_ICON)) {
                    setIcon((ImageResource) (act.getValue(Action.SMALL_ICON)));
                } else if (e.getPropertyName().equals(Action.SHORT_DESCRIPTION)) {
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

}
