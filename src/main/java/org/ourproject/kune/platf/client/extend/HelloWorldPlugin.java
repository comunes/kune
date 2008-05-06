/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.ourproject.kune.platf.client.extend;

import org.ourproject.kune.platf.client.PlatformEvents;
import org.ourproject.kune.platf.client.View;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

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

        getDispatcher().fire(PlatformEvents.ATTACH_TO_EXT_POINT,
                new UIExtensionPair(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, helloWorld.getView()));

    }

    @Override
    protected void stop() {
        getDispatcher().fire(PlatformEvents.DETACH_FROM_EXT_POINT,
                new UIExtensionPair(UIExtensionPoint.CONTENT_BOTTOM_ICONBAR, helloWorld.getView()));

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

        public void registerExtPoint(final String id, final UIExtensible extPoint) {
            getExtensionPointManager().registerUIExtensionPoint(id, extPoint);
        }
    }

    interface HelloWorldView extends View {
    }

    class HelloWorldPanel extends Composite implements HelloWorldView, UIExtensible {

        private static final String HELLOWORLDPLUGIN_EXT_POINT = "helloworldplugin.extpoint";
        private final HorizontalPanel hp;

        public HelloWorldPanel(final HelloWorldPresenter presenter) {
            hp = new HorizontalPanel();
            initWidget(hp);
            hp.add(new Label("Hello World"));

            // Registering a new Extension Point
            presenter.registerExtPoint(HELLOWORLDPLUGIN_EXT_POINT, this);
        }

        public void attach(final String id, final Widget widget) {
            if (id.equals(HELLOWORLDPLUGIN_EXT_POINT)) {
                hp.add(widget);
            }
        }

        public void detach(final String id, final Widget widget) {
            if (id.equals(HELLOWORLDPLUGIN_EXT_POINT)) {
                hp.remove(widget);
            }
        }

        public void detachAll(final String id) {
            if (id.equals(HELLOWORLDPLUGIN_EXT_POINT)) {
                hp.clear();
            }
        }
    }

}
