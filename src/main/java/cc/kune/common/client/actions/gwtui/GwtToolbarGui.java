package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.google.gwt.user.client.ui.UIObject;

public class GwtToolbarGui extends AbstractGuiItem implements ParentWidget {

    private GwtComplexToolbar toolbar;

    public GwtToolbarGui() {
    }

    public GwtToolbarGui(final AbstractGuiActionDescrip descriptor) {
        super(descriptor);

    }

    @Override
    public void add(final UIObject uiObject) {
        toolbar.add(uiObject);
    }

    public void addFill() {
        toolbar.addFill();
    }

    public void addSeparator() {
        toolbar.addSeparator();
    }

    public void addSpacer() {
        toolbar.addSpacer();
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        toolbar = new GwtComplexToolbar();
        initWidget(toolbar);
        configureItemFromProperties();
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        return this;
    }

    @Override
    public void insert(final int position, final UIObject widget) {
        toolbar.insert(widget, position);
    }

    @Override
    protected void setEnabled(final boolean enabled) {
    }

    @Override
    protected void setIconStyle(final String style) {
    }

    @Override
    protected void setText(final String text) {
    }

    @Override
    protected void setToolTipText(final String text) {
        toolbar.setTitle(text);
    }

    @Override
    public boolean shouldBeAdded() {
        return true;
    }

}
