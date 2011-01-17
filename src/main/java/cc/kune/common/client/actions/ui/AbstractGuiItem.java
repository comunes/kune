package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.bind.GuiBinding;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractGuiItem extends Composite implements GuiBinding {

    protected AbstractGuiActionDescrip descriptor;

    public AbstractGuiItem() {
        super();
    }

    public AbstractGuiItem(final AbstractGuiActionDescrip descriptor) {
        super();
        this.descriptor = descriptor;
    }

    private void configure() {
        configureProperties();
        final PropertyChangeListener changeListener = createActionPropertyChangeListener();
        descriptor.getAction().addPropertyChangeListener(changeListener);
        descriptor.addPropertyChangeListener(changeListener);
    }

    /**
     * Sets the item properties from the stored values
     */
    public void configureItemFromProperties() {
        configure();
    }

    private void configureProperties() {
        setText((String) (descriptor.getValue(Action.NAME)));
        setIcon(descriptor.getValue(Action.SMALL_ICON));
        setEnabled((Boolean) descriptor.getValue(AbstractAction.ENABLED));
        setToolTipText((String) (descriptor.getValue(Action.SHORT_DESCRIPTION)));
        setVisible((Boolean) descriptor.getValue(AbstractGuiActionDescrip.VISIBLE));
    }

    @Override
    public abstract AbstractGuiItem create(final AbstractGuiActionDescrip descriptor);

    private PropertyChangeListener createActionPropertyChangeListener() {
        return new PropertyChangeListener() {
            @Override
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
                } else if (event.getPropertyName().equals(AbstractGuiActionDescrip.VISIBLE)) {
                    setVisible((Boolean) newValue);
                }
            }
        };
    }

    protected abstract void setEnabled(boolean enabled);

    private void setEnabled(final Boolean enabled) {
        setEnabled(enabled == null ? true : enabled);
    }

    private void setIcon(final Object icon) {
        if (icon != null) {
            setIconStyle((String) icon);
        }
    }

    protected abstract void setIconStyle(String style);

    protected abstract void setText(String text);

    protected abstract void setToolTipText(String text);

    private void setVisible(final Boolean visible) {
        setVisible(visible == null ? true : visible);
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }
}
