
package org.ourproject.kune.chat.client.cnt;

import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RoomViewPanel extends VerticalPanel implements View {

    public RoomViewPanel(final RoomViewListener listener) {
	FlowPanel flow = new FlowPanel();
	Button btnEnter = new Button("entrar", new ClickListener() {
	    public void onClick(Widget arg0) {
		listener.onEnterRoom();
	    }
	});
	flow.add(btnEnter);
	Label label = new Label("panel del chat: contenido por defecto");

	add(flow);
	add(label);
    }
}
