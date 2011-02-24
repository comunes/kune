package cc.kune.common.client.actions.ui.descrip;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeListener;

public interface GuiActionDescrip {

    public static final AbstractGuiActionDescrip NO_PARENT = new NoParentGuiActionDescriptor();

    public static final int NO_POSITION = -1;

    /**
     * {@link #TARGET} is used to associate a {@link #AbstractGuiActionDescrip}
     * with an object like groups, group names, users, and so on, and used to
     * execute actions against these targets
     */
    public static final String TARGET = "target";

    public static final String VISIBLE = "visibleprop";

    void add(final GuiAddCondition addCondition);

    void addPropertyChangeListener(final PropertyChangeListener listener);

    void fire(final ActionEvent event);

    AbstractAction getAction();

    String getId();

    Object[] getKeys();

    String getLocation();

    GuiActionDescrip getParent();

    int getPosition();

    PropertyChangeListener[] getPropertyChangeListeners();

    Object getTarget();

    Class<?> getType();

    Object getValue(final String key);

    boolean hasTarget();

    boolean isChild();

    boolean isEnabled();

    boolean isVisible();

    boolean mustBeAdded();

    public void putValue(final String key, final Object value);

    void removePropertyChangeListener(final PropertyChangeListener listener);

    void setEnabled(final boolean enabled);

    void setId(final String id);

    void setLocation(final String location);

    void setParent(final GuiActionDescrip parent);

    void setPosition(final int position);

    void setStyles(final String styles);

    void setTarget(final Object object);

    void setVisible(final boolean visible);

    GuiActionDescrip withIcon(Object icon);

    GuiActionDescrip withIconCls(String icon);

    GuiActionDescrip withParent(GuiActionDescrip parent);

    GuiActionDescrip withStyles(String styles);

    GuiActionDescrip withText(String text);

    GuiActionDescrip withToolTip(String tooltip);

}
