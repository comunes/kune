/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client.hello;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.actions.ActionToolbarMenuDescriptor;
import org.ourproject.kune.platf.client.actions.ActionToolbarPosition;
import org.ourproject.kune.platf.client.actions.UserActionRegistry;
import org.ourproject.kune.platf.client.dto.AccessRolDTO;
import org.ourproject.kune.platf.client.dto.UserSimpleDTO;
import org.ourproject.kune.platf.client.i18n.I18nTranslationService;
import org.ourproject.kune.platf.client.i18n.I18nTranslationServiceMocked;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.user.client.ui.Label;

/**
 * This module is installed via Suco.install(new HelloWorldModule()). In kune we
 * do this normally in KuneEntryPoint.onModuleLoadCont()
 */
public class HelloWorldModule extends AbstractModule {

    /**
     * 
     * In this Module we include the HelloWorld/View/Panel/Presenter classes
     * only for commodity reasons. They reflect our use of the MVN pattern.
     * 
     * Normally we generate a snippet for this pattern using:
     * 
     * <pre>
     * script / generateBasicUIElements.sh
     * </pre>
     * 
     * HelloWorld is the external interface of this four classes. We use it if
     * other classes have to interact with HelloWorld.
     * 
     */
    public interface HelloWorld {
    }

    /**
     * In the Panel classes we do all the UI stuff (with none business logic)
     * 
     */
    public class HelloWorldPanel implements HelloWorldView {
        private final I18nTranslationService i18n;

        public HelloWorldPanel(final HelloWorldPresenter presenter, final WorkspaceSkeleton ws,
                final I18nTranslationService i18n) {
            this.i18n = i18n;
            /** We can directly insert something in the workspace skeleton **/
            ws.getEntityWorkspace().getSubTitle().add(new Label(i18n.t("Hello world!")));
        }

        public void showMessage() {
            /** i18n use with parameters **/
            NotifyUser.info(i18n.t("Hello [%s]!", "world"));
        }
    }

    public class HelloWorldPresenter implements HelloWorld {

        private HelloWorldView view;
        private final Provider<UserActionRegistry> actionRegistry;
        private final I18nTranslationService i18n;

        public HelloWorldPresenter(I18nTranslationService i18n, Provider<UserActionRegistry> actionRegistry) {
            this.i18n = i18n;
            this.actionRegistry = actionRegistry;
            createActions();
        }

        public View getView() {
            return view;
        }

        public void init(HelloWorldView view) {
            this.view = view;
        }

        /**
         * We add an menu action to users menus (like the buddies in Buddies
         * Summary)
         **/
        private void createActions() {
            ActionToolbarMenuDescriptor<UserSimpleDTO> helloWorldBuddiesAction = new ActionToolbarMenuDescriptor<UserSimpleDTO>(
                    AccessRolDTO.Viewer, ActionToolbarPosition.bottombar, new Listener<UserSimpleDTO>() {
                        public void onEvent(UserSimpleDTO parameter) {
                            // We clicked:
                            view.showMessage();
                        }
                    });
            // AccessRolDTO.Viewer: any user can see this option and without be
            // authenticated:
            helloWorldBuddiesAction.setMustBeAuthenticated(false);
            // We add a submenu in Options menu:
            helloWorldBuddiesAction.setParentMenuTitle(i18n.t("Options"));
            helloWorldBuddiesAction.setParentSubMenuTitle(i18n.t("Hello world submenu"));
            helloWorldBuddiesAction.setTextDescription(i18n.t("Hello world menu item"));
            helloWorldBuddiesAction.setIconUrl("images/info.gif");

            // Lazy creation of objects:
            //
            // Using Provider<Class> we do lazy instantiation. When we call
            // actionRegistry.get() it creates the instance if is not created
            // yet.
            actionRegistry.get().addAction(helloWorldBuddiesAction);
        }
    }

    /**
     * We use this interface between the Presenter and the Panel. In tests it
     * cat be easy mocked.
     */
    public interface HelloWorldView extends View {
        void showMessage();
    }

    /**
     * This is the real part of the module (when we do how and when to create
     * the above classes, how many instances (singleton, one per use), and so
     * on).
     * 
     * The above clases are included here only for commodity purposes
     */
    @Override
    protected void onInstall() {
        /**
         * 
         * Now we register how to create instances of HelloWorld, and later we
         * can use then from other objects.
         * 
         * If we want to use HelloWorld from other modules we can use:
         * $(HelloWorld.class) to get a instance or $$(HelloWorld.class) to get
         * a provider of the instance. This is useful when we want to do lazy
         * creation of objects: http://en.wikipedia.org/wiki/Lazy_initialization
         * 
         **/
        register(Singleton.class, new Factory<HelloWorld>(HelloWorld.class) {
            /**
             * Singleton.class decorator: creates only one instance of
             * HelloWorld.class and when we use it the provider returns always
             * the same instance.
             * 
             * NoDecorator.class: Use it if we need to create a new instance of
             * HelloWorld.class in every use.
             */
            @Override
            public HelloWorld create() {
                final HelloWorldPresenter presenter = new HelloWorldPresenter($(I18nTranslationServiceMocked.class),
                        $$(UserActionRegistry.class));
                final HelloWorldPanel panel = new HelloWorldPanel(presenter, $(WorkspaceSkeleton.class),
                        $(I18nTranslationServiceMocked.class));
                presenter.init(panel);
                return presenter;
                // now Suco knows how to create HelloWorld instance when we use
                // $(HelloWorld.class) or $$(HelloWorld.class in a module.
            }

            /**
             * Optionally: we can do something after the instance is created
             **/
            @Override
            public void onAfterCreated(HelloWorld instance) {
                Log.info("HelloWorld singleton instance created");
            }
        });

        /**
         * we use a mock for i18n in this sample because we don't really want to
         * translate this module messages. Normally we use:
         * I18nTranslationService.class instead of the mock
         **/
        register(Singleton.class, new Factory<I18nTranslationServiceMocked>(I18nTranslationServiceMocked.class) {
            @Override
            public I18nTranslationServiceMocked create() {
                return new I18nTranslationServiceMocked();
            }
        });

        // And because nobody use this module, we get the class (to force the
        // creation of the
        // Helloworld instance):
        // $(HelloWorld.class);
    }
}