package org.ourproject.kune.app.client;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.app.ui.UIExtensionPoint;
import org.ourproject.kune.workspace.client.WorkspaceEvents;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

public class HelloWorldPlugin extends Plugin {

    private HelloWorldPresenter helloWorld;

    public HelloWorldPlugin() {
        super("HelloWorldPlugin");
    }

    @Override
    protected void start() {
        helloWorld = new HelloWorldPresenter();
        HelloWorldPanel panel = new HelloWorldPanel(helloWorld);
        helloWorld.init(panel);

        getDisplacher().fire(WorkspaceEvents.ATTACH_TO_EXT_POINT, UIExtensionPoint.CONTENT_BOTTOM_ICONBAR,
                helloWorld.getView());

    }

    @Override
    protected void stop() {
        getDisplacher().fire(WorkspaceEvents.DETACH_FROM_EXT_POINT, UIExtensionPoint.CONTENT_BOTTOM_ICONBAR,
                helloWorld.getView());

    }

    class HelloWorldPresenter implements View {
        private HelloWorldView view;

        public HelloWorldPresenter() {
        }

        void init(final HelloWorldView view) {
            this.view = view;
        }

        public View getView() {
            return view;
        }

        public void registerExtPoint(final UIExtensionPoint extPoint) {
            getExtensionPointManager().addUIExtensionPoint(extPoint);
        }
    }

    interface HelloWorldView extends View {
    }

    class HelloWorldPanel extends Composite implements HelloWorldView {
        private final HorizontalPanel hp;

        public HelloWorldPanel(final HelloWorldPresenter presenter) {
            hp = new HorizontalPanel();
            initWidget(hp);
            hp.add(new Label("Hello World"));

            // Registering a new Extension Point
            UIExtensionPoint extPoint = new UIExtensionPoint("helloworldplugin.hp", hp);
            presenter.registerExtPoint(extPoint);
        }
    }

}
