package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeListener;

public interface GuiActionDescrip {

    /**
     * {@link #ITEM} is used to associate a {@link #AbstractGuiActionDescrip}
     * with an object like groups, group names, users, and so on, and used to
     * execute actions with this item as parameter
     */
    public static final String ITEM = "item";

    public static final AbstractGuiActionDescrip NO_PARENT = new NoParentGuiActionDescriptor();

    public static final int NO_POSITION = -1;

    /**
     * Coma separated, styles of the Gui item
     */
    public static final String STYLES = "stylesprop";

    public static final String VISIBLE = "visibleprop";

    void addPropertyChangeListener(final PropertyChangeListener listener);

    void add(final GuiAddCondition addCondition);

    void fire(final ActionEvent event);

    AbstractAction getAction();

    String getId();

    Object getItem();

    Object[] getKeys();

    String getLocation();

    GuiActionDescrip getParent();

    int getPosition();

    PropertyChangeListener[] getPropertyChangeListeners();

    Class<?> getType();

    Object getValue(final String key);

    boolean hasItem();

    boolean isChild();

    boolean isEnabled();

    boolean isVisible();

    boolean mustBeAdded();

    public void putValue(final String key, final Object value);

    void removePropertyChangeListener(final PropertyChangeListener listener);

    void setEnabled(final boolean enabled);

    void setId(final String id);

    void setItem(final Object object);

    void setLocation(final String location);

    void setParent(final GuiActionDescrip parent);

    void setPosition(final int position);

    void setStyles(final String styles);

    void setVisible(final boolean visible);

}
