package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.ui.img.ImgConstants;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.widgets.ToolbarButton;

public class MenuGui extends AbstractMenuGui {

    private final ToolbarButton button;

    public MenuGui(final GuiActionDescrip descriptor) {
        super();
        button = new ToolbarButton();
        button.setMenu(menu);
        setAction(descriptor.action);
        initWidget(button);
        descriptor.action.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    menu.hide(true);
                }
            }
        });
    }

    public void add(final SubMenuGui submenu) {
        menu.addItem(submenu.getMenuItem());
    };

    public void insert(final int position, final SubMenuGui submenu) {
        menu.insert(position, submenu.getMenuItem());
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (enabled) {
            button.enable();
        } else {
            button.disable();
        }
    }

    @Override
    public void setIcon(final ImageResource imageResource) {
        if (imageResource != null) {
            button.setIcon(ImgConstants.PATH_PREFIX + imageResource.getName() + ".png");
        }
    }

    @Override
    public void setText(final String text) {
        button.setText(text);
    }

    @Override
    public void setToolTipText(final String tooltip) {
        button.setTooltip(tooltip);
    }
}
