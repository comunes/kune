package org.ourproject.kune.platf.client.actions.ui;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.img.RTEImgResources;

import com.google.gwt.libideas.resources.client.ImageResource;
import com.gwtext.client.widgets.ToolbarButton;

public class DefaultMenu extends AbstractMenu {

    // @PMD:REVIEWED:AtLeastOneConstructor: by vjrj on 26/05/09 15:56
    public class MenuButton extends ToolbarButton implements View {
    }

    private transient final MenuButton button;

    public DefaultMenu(final AbstractUIActionDescriptor descriptor) {
        super();
        button = new MenuButton();
        button.setMenu(menu);
        setAction(descriptor.action);
        initWidget(menu);
    }

    public void add(final DefaultSubMenu submenu) {
        menu.addItem(submenu.getMenuItem());
    };

    public MenuButton getButton() {
        return button;
    }

    public void insert(final int position, final DefaultSubMenu submenu) {
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
            // FIXME
            button.setIconCls(RTEImgResources.SUFFIX + imageResource.getName());
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
