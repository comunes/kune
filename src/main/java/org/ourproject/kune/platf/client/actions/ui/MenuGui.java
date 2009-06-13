package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.actions.PropertyChangeEvent;
import org.ourproject.kune.platf.client.actions.PropertyChangeListener;
import org.ourproject.kune.platf.client.ui.img.ImgConstants;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.widgets.ToolbarButton;

public class MenuGui extends AbstractMenuGui {

    private final boolean notStandAlone;
    private ToolbarButton button = null;

    public MenuGui(final MenuDescriptor descriptor) {
        super();
        setAction(descriptor.action);
        // Standalone menus are menus without and associated button in a toolbar
        // (sometimes, a menu showed in a grid, or other special widgets)
        notStandAlone = !descriptor.isStandalone();
        if (notStandAlone) {
            button = new ToolbarButton();
            button.setMenu(menu);
            initWidget(button);
        }
        descriptor.action.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(final PropertyChangeEvent event) {
                if (event.getPropertyName().equals(MenuDescriptor.MENU_HIDE)) {
                    menu.hide(true);
                }
            }
        });
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (notStandAlone) {
            if (enabled) {
                button.enable();
            } else {
                button.disable();
            }
        }
    }

    @Override
    public void setIcon(final ImageResource imageResource) {
        if (notStandAlone) {
            if (imageResource != null) {
                button.setIcon(ImgConstants.PATH_PREFIX + imageResource.getName() + ".png");
            }
        }
    }

    @Override
    public void setText(final String text) {
        if (notStandAlone) {
            button.setText(text);
        }
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (notStandAlone) {
            button.setTooltip(tooltip);
        }
    }
}
