package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.ui.IconLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;

public class GwtMenuGui extends AbstractGwtMenuGui {

    private Button button;
    private IconLabel iconLabel;
    private boolean notStandAlone;

    public GwtMenuGui() {
    }

    public GwtMenuGui(final MenuDescriptor descriptor) {
        super(descriptor);
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        // Standalone menus are menus without and associated button in a toolbar
        // (sometimes, a menu showed in a grid, or other special widgets)
        notStandAlone = !((MenuDescriptor) descriptor).isStandalone();
        if (notStandAlone) {
            button = new Button();
            button.setStylePrimaryName("oc-button");
            iconLabel = new IconLabel("");
            button.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(final ClickEvent event) {
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
        } else {
            initWidget(new Label());
        }
        super.create(descriptor);
        configureItemFromProperties();
        return this;
    }

    private void layout() {
        button.setHTML(iconLabel.getElement().getInnerHTML());
    }

    @Override
    public void setEnabled(final boolean enabled) {
        if (notStandAlone) {
            button.setVisible(enabled);
        }
    }

    @Override
    public void setIconStyle(final String style) {
        if (notStandAlone) {
            iconLabel.setIcon(style);
            layout();
        }
    }

    @Override
    public void setText(final String text) {
        if (notStandAlone) {
            iconLabel.setText(text);
            layout();
        }
    }

    @Override
    public void setToolTipText(final String tooltip) {
        if (notStandAlone) {
            // button.setTooltip(tooltip);
            button.setTitle(tooltip);
        }
    }

    @Override
    public void setVisible(final boolean visible) {
        if (notStandAlone) {
            button.setVisible(visible);
        } else {
            // button.setVisible(visible);
        }
    }

}
