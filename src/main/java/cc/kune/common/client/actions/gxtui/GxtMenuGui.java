package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.SplitButton;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;
import com.google.gwt.user.client.ui.UIObject;

public class GxtMenuGui extends AbstractGxtMenuGui implements ParentWidget {

    private SplitButton button;
    private boolean notStandAlone;

    public GxtMenuGui() {
        super();
    }

    @Override
    public void add(final UIObject item) {
        menu.add((MenuItem) item);
    }

    @Override
    public void addSeparator() {
        menu.add(new SeparatorMenuItem());
    }

    @Override
    public void configureItemFromProperties() {
        super.configureItemFromProperties();
        descriptor.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_CLEAR)) {
                    menu.removeAll();
                }
            }
        });
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        // Standalone menus are menus without and associated button in a
        // toolbar
        // (sometimes, a menu showed in a grid, or other special widgets)
        notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
        if (notStandAlone) {
            button = new SplitButton("");
            button.setStylePrimaryName("oc-button");
            button.addSelectionListener(new SelectionListener<ButtonEvent>() {
                @Override
                public void componentSelected(final ButtonEvent ce) {
                    show(button);
                }
            });
            final String id = descriptor.getId();
            if (id != null) {
                button.ensureDebugId(id);
            }
            if (!descriptor.isChild()) {
                initWidget(button);
            } else {
                child = button;
            }
        }
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    @Override
    public void insert(final int position, final UIObject item) {
        menu.insert((MenuItem) item, position);
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (button != null) {
            button.setEnabled(enabled);
        }

    }

    @Override
    public void setIconStyle(final String style) {
        if (button != null) {
            button.setIconStyle(style);
        }

    }

    @Override
    public void setText(final String text) {
        if (button != null) {
            button.setText(text);
        }

    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (button != null) {
            button.setToolTip(new GxtDefTooltip(tooltip));
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (button != null) {
            button.setVisible(visible);
        }

    }
}
