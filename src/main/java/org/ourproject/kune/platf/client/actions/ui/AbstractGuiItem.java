package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.ui.img.ImgConstants;

import com.google.gwt.libideas.resources.client.ImageResource;
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
            setIconStyle((ImgConstants.CSS_SUFFIX + ((ImageResource) icon).getName()));
        } else if (icon instanceof String) {
            setIconUrl((String) icon);
        }
    }

    private void setVisible(final Boolean visible) {
        setVisible(visible == null ? true : visible);
    }
}
