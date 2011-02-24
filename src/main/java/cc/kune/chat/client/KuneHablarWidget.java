package cc.kune.chat.client;

import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.HablarDisplay;
import com.calclab.hablar.core.client.HablarPresenter;
import com.calclab.hablar.core.client.mvp.DefaultEventBus;
import com.calclab.hablar.core.client.pages.tabs.TabsLayout.TabHeaderSize;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class KuneHablarWidget extends LayoutPanel implements HablarDisplay {
    public class HablarNoLoggerEventBus extends DefaultEventBus {

        @Override
        public void fireEvent(final GwtEvent<?> event) {
            // GWT.log("EVENT: " + event.toDebugString(), null);
            super.fireEvent(event);
        }
    }

    private final Hablar hablar;

    @UiConstructor
    public KuneHablarWidget(final Layout layout, final TabHeaderSize tabHeaderSize) {
        addStyleName("hablar-HablarWidget");
        if (layout == Layout.accordion) {
            hablar = HablarPresenter.createAccordionPresenter(new HablarNoLoggerEventBus(), this);
        } else if (layout == Layout.tabs) {
            hablar = HablarPresenter.createTabsPresenter(new HablarNoLoggerEventBus(), this, tabHeaderSize);
        } else {
            throw new IllegalStateException("Unimplemented layout: " + layout);
        }
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    @Override
    public void forceLayout() {
        // GWT.log("FORCE LAYOUT");
        super.forceLayout();
    }

    public Hablar getHablar() {
        return hablar;
    }

}
