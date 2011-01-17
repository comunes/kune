package cc.kune.common.client.actions.gwtui;

import cc.kune.common.client.actions.ui.AbstractGuiItem;
import cc.kune.common.client.ui.FlowToolbar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;

public class GwtComplexToolbar extends Composite implements IsWidget {

    private enum FlowDir {
        left, right
    }
    private FlowDir currentFlow;
    private final FlowToolbar toolbar;

    public GwtComplexToolbar() {
        toolbar = new FlowToolbar();
        currentFlow = FlowDir.left;
        initWidget(toolbar);
    }

    protected void add(final AbstractGuiItem item) {
        item.addStyleName(getFlow());
        toolbar.add(item);
    }

    public void add(final UIObject uiObject) {
        toolbar.add((Widget) uiObject);
    }

    public void addFill() {
        currentFlow = FlowDir.right;
        toolbar.addFill();
    }

    public void addSeparator() {
        toolbar.addSeparator();
    }

    public void addSpacer() {
        toolbar.addSpacer();
    }

    private String getFlow() {
        switch (currentFlow) {
        case left:
            return "oc-floatleft";
        case right:
        default:
            return "oc-floatright";
        }
    }

    protected void insert(final AbstractGuiItem item, final int position) {
        item.addStyleName(getFlow());
        toolbar.insert(item, position);
    }

    public void insert(final UIObject uiObject, final int position) {
        toolbar.insert((Widget) uiObject, position);
    }

    /**
     * Set the blank style
     */
    public void setCleanStyle() {
        toolbar.setBlankStyle();
    }

    /**
     * Set the normal grey style
     */
    public void setNormalStyle() {
        toolbar.setNormalStyle();
    }

    /**
     * Set the blank style
     */
    public void setTranspStyle() {
        toolbar.setTranspStyle();
    }

}
