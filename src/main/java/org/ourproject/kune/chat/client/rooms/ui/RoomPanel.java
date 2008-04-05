package org.ourproject.kune.chat.client.rooms.ui;

import org.ourproject.kune.chat.client.rooms.RoomPresenter;
import org.ourproject.kune.chat.client.rooms.RoomView;
import org.ourproject.kune.platf.client.ui.HorizontalLine;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class RoomPanel extends Panel implements RoomView {
    private final VerticalPanel vp;

    public RoomPanel(final RoomPresenter presenter) {
        setClosable(true);
        // Test to remoeve AuthoHeight and see if scroll works using this panel
        // in scrollDown
        setAutoScroll(true);
        setBorder(false);
        setAutoHeight(true);
        vp = new VerticalPanel();
        add(vp);
        addStyleName("kune-RoomPanel-Conversation");
    }

    public void showRoomName(final String name) {
        setTitle(name);
    }

    public void showInfoMessage(final String message) {
        HTML messageHtml = new HTML(message);
        addWidget(messageHtml);
        messageHtml.addStyleName("kune-RoomPanel-EventMessage");
    }

    public void showMessage(final String userAlias, final String color, final String message) {
        // FIXME: Use gwt DOM.create... for this:
        String userHtml = "<span style=\"color: " + color + ";\">" + userAlias + "</span>:&nbsp;";
        HTML messageHtml = new HTML(userHtml + ChatTextFormatter.format(message).getHTML());
        addWidget(messageHtml);
    }

    public void showDelimiter(final String datetime) {
        HorizontalPanel hp = new HorizontalPanel();
        HorizontalLine hr = new HorizontalLine();
        hp.add(new Label(datetime));
        hp.add(hr);
        hp.setWidth("100%");
        hp.setCellWidth(hr, "100%");
        addWidget(hp);
        hp.setStyleName("kune-RoomPanel-HorizDelimiter");
    }

    public void scrollDown() {
        DOM.setElementPropertyInt(DOM.getParent(this.getElement()), "scrollTop", vp.getOffsetHeight());
    }

    private void addWidget(final Widget widget) {
        vp.add(widget);
        widget.addStyleName("kune-RoomPanel-Message");
        DOM.setElementPropertyInt(DOM.getParent(this.getElement()), "scrollTop", vp.getOffsetHeight());
    }
}
