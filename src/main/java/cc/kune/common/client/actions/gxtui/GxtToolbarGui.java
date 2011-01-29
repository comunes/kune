package cc.kune.common.client.actions.gxtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.actions.ui.ParentWidget;
import cc.kune.common.client.actions.ui.descrip.AbstractGuiActionDescrip;

import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LabelToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.ui.UIObject;

public class GxtToolbarGui extends AbstractGuiItem implements ParentWidget {

    private ToolBar toolbar;

    public GxtToolbarGui() {
        super();
    }

    public GxtToolbarGui(final AbstractGuiActionDescrip descriptor) {
        super(descriptor);
    }

    @Override
    public void add(final UIObject uiObject) {
        toolbar.add((Component) uiObject);
    }

    public FillToolItem addFill() {
        final FillToolItem item = new FillToolItem();
        toolbar.add(item);
        return item;
    }

    public SeparatorToolItem addSeparator() {
        final SeparatorToolItem item = new SeparatorToolItem();
        toolbar.add(item);
        return item;
    }

    public LabelToolItem addSpacer() {
        final LabelToolItem item = new LabelToolItem();
        toolbar.add(item);
        return item;
    }

    @Override
    public AbstractGuiItem create(final AbstractGuiActionDescrip descriptor) {
        super.descriptor = descriptor;
        toolbar = new ToolBar();
        initWidget(toolbar);
        configureItemFromProperties();
        descriptor.putValue(ParentWidget.PARENT_UI, this);
        return this;
    }

    @Override
    public void insert(final int position, final UIObject uiObject) {
        toolbar.insert((Component) uiObject, position);
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

}
