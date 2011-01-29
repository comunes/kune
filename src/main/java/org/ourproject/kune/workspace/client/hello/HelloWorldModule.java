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
import org.ourproject.kune.platf.client.actions.AbstractAction;
import org.ourproject.kune.platf.client.actions.AbstractExtendedAction;
import org.ourproject.kune.platf.client.actions.Action;
import org.ourproject.kune.platf.client.actions.ActionEvent;
import org.ourproject.kune.platf.client.actions.Shortcut;
import org.ourproject.kune.platf.client.actions.ui.MenuCheckItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuRadioItemDescriptor;
import org.ourproject.kune.platf.client.actions.ui.MenuSeparatorDescriptor;
import org.ourproject.kune.platf.client.actions.ui.PushButtonDescriptor;
import org.ourproject.kune.platf.client.services.AbstractExtendedModule;
import org.ourproject.kune.platf.client.shortcuts.GlobalShortcutRegister;
import org.ourproject.kune.platf.client.ui.noti.NotifyUser;
import org.ourproject.kune.workspace.client.editor.ContentEditor;
import org.ourproject.kune.workspace.client.hello.HelloWorldModule.HelloWorldPanel.HelloWorldPresenter;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import cc.kune.core.client.i18n.I18nTranslationServiceMocked;
import cc.kune.core.client.resources.icons.IconResources;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.ioc.Provider;
import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Label;

/**
 * This module is installed via Suco.install(new HelloWorldModule()). In kune we
 * do this normally in KuneEntryPoint.onModuleLoadCont()
 */
public class HelloWorldModule extends AbstractExtendedModule {

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
    public static class HelloWorldPanel implements HelloWorldView {
        public static class HelloWorldPresenter implements HelloWorld {

            private HelloWorldView view;

            public HelloWorldPresenter(final I18nTranslationService i18n,
                    final Provider<ContentEditor> contentEditorProv, final IconResources img) {
                // Or add some actions to the general content editor:
                createActions(contentEditorProv, img, i18n);
            }

            public View getView() {
                return view;
            }

            public void init(final HelloWorldView view) {
                this.view = view;
            }

            /**
             * We can add some gui action items to the content editor
             * 
             * Summary).
             * 
             * @param i18n
             *            the i18n
             * @param contentEditorProv
             *            the content editor provider
             * @param img
             *            the img
             */
            private void createActions(final Provider<ContentEditor> contentEditorProv, final IconResources img,
                    final I18nTranslationService i18n) {

                final HelloWorldAction action = new HelloWorldAction(img);

                // We describe some gui items and all will use the same action:

                final PushButtonDescriptor btn = new PushButtonDescriptor(action);

                // We no use i18n.t() in most of these gui widgets but it's is
                // recommended the use in real code
                final MenuDescriptor menu = new MenuDescriptor(i18n.t("HelloWorldMenu"), "File menu tooltip");

                final MenuItemDescriptor menuitem = new MenuItemDescriptor(menu, action);
                final MenuItemDescriptor menuitem2 = new MenuItemDescriptor(menu, action);

                // A submenu
                final MenuDescriptor submenu = new MenuDescriptor("Options", "Submenu tooltip");
                submenu.setParent(menu);

                // A menu check item
                final MenuItemDescriptor menuitem3 = new MenuItemDescriptor(submenu, action);
                final MenuCheckItemDescriptor menuitem4 = new MenuCheckItemDescriptor(submenu, action);
                menuitem4.setChecked(true);

                // Some menu radio items
                final String radioGroup = "helloworldradiogroup";
                final MenuRadioItemDescriptor menuitem5 = new MenuRadioItemDescriptor(submenu, action, radioGroup);
                final MenuRadioItemDescriptor menuitem6 = new MenuRadioItemDescriptor(submenu, action, radioGroup);
                menuitem5.setChecked(true);

                // We can define also some menu separators
                final MenuSeparatorDescriptor menuSeparator = new MenuSeparatorDescriptor(menu);
                final MenuSeparatorDescriptor otherSeparator = new MenuSeparatorDescriptor(submenu);

                // Lazy creation of objects:
                //
                // Using Provider<Class> we do lazy instantiation. When we call
                // actionRegistry.get() it creates the instance if is not
                // created yet.
                final ContentEditor contentEditor = contentEditorProv.get();

                // We want to add some items to the topbar and other to the
                // second bar of the editor
                contentEditor.setLocation(ContentEditor.TOPBAR, menu, menuitem, menuSeparator, menuitem2, submenu,
                        menuitem3, menuitem4, otherSeparator, menuitem5, menuitem6);
                contentEditor.setLocation(ContentEditor.SNDBAR, btn);

                // And finally we add the action descriptors to the editor:
                contentEditor.addActions(btn, menu, menuitem);

                // Also individually
                contentEditor.addAction(menuSeparator);

                // It's important to add the menus before its menu items
                contentEditor.addActions(menuitem2, submenu, menuitem3, menuitem4, otherSeparator, menuitem5, menuitem6);

                // We can change descriptors properties after render and the UI
                // is updated automatically
                btn.setPushed(true);

                // After some time we can change the common action or some other
                // properties
                new Timer() {
                    @Override
                    public void run() {
                        // The text of the action (in the menu or in the button)
                        action.putValue(Action.NAME, "hello world (updated)");
                        action.putValue(Action.SHORT_DESCRIPTION, "hello world tooltip (updated)");
                        btn.setPushed(false);
                    }
                }.schedule(15000);
            }
        }

        private final I18nTranslationService i18n;

        public HelloWorldPanel(final HelloWorldPresenter presenter, final WorkspaceSkeleton wspace,
                final I18nTranslationService i18n) {
            this.i18n = i18n;
            // We can directly insert something in the workspace skeleton
            wspace.getEntityWorkspace().getSubTitle().add(new Label(i18n.t("Hello world!")));
        }

        public void showMessage() {
            // i18n use with parameters
            NotifyUser.info(i18n.t("Hello [%s]!", "world"));
        }
    }

    /**
     * We use this interface between the Presenter and the Panel. In tests it
     * cat be easy mocked.
     */
    public interface HelloWorldView extends View {
        void showMessage();
    }

    static class HelloWorldAction extends AbstractAction {
        public HelloWorldAction(final IconResources img) {
            super();
            super.putValue(Action.NAME, "helloworld");
            super.putValue(Action.SHORT_DESCRIPTION, "helloworld tooltip");
            super.putValue(Action.SMALL_ICON, img.info());
        }

        public void actionPerformed(final ActionEvent actionEvent) {
            if (actionEvent.getEvent().getCtrlKey()) {
                NotifyUser.info("Hello world action fired with ctrl key pressed");
            } else {
                NotifyUser.info("Hello world action fired");
            }
        }
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
         * i(HelloWorld.class) to get a instance or p(HelloWorld.class) to get a
         * provider of the instance. This is useful when we want to do lazy
         * creation of objects: http://en.wikipedia.org/wiki/Lazy_initialization
         * 
         **/
        register(Singleton.class, new Factory<HelloWorld>(HelloWorld.class) {
            /**
             * Singleton.class decorator: creates only one instance of
             * HelloWorld.class and when we use it, the provider returns always
             * the same instance.
             * 
             * NoDecorator.class: Use it if we need to create a new instance of
             * HelloWorld.class in every use.
             * 
             * Others:
             * 
             * ApplicationComponentGroup.class: Use this decorator with parts of
             * kune that are needed at start time (like main widgets in
             * workspace). They are created automatically when starting.
             * 
             */
            @Override
            public HelloWorld create() {
                final HelloWorldPresenter presenter = new HelloWorldPresenter(i(I18nTranslationServiceMocked.class),
                        p(ContentEditor.class), i(IconResources.class));
                final HelloWorldPanel panel = new HelloWorldPanel(presenter, i(WorkspaceSkeleton.class),
                        i(I18nTranslationServiceMocked.class));
                presenter.init(panel);
                return presenter;
                // now Suco knows how to create HelloWorld instance when we use
                // i(HelloWorld.class) or p(HelloWorld.class in a module.
            }

            /**
             * Optionally: we can do something after the instance is created
             */
            @Override
            public void onAfterCreated(final HelloWorld instance) {
                Log.info("HelloWorld singleton instance created");
            }
        });

        /**
         * we use a mock for i18n in this sample because we don't really want to
         * translate this module messages. Normally we use:
         * I18nTranslationService.class instead of the mock
         */
        register(Singleton.class, new Factory<I18nTranslationServiceMocked>(I18nTranslationServiceMocked.class) {
            @Override
            public I18nTranslationServiceMocked create() {
                return new I18nTranslationServiceMocked();
            }
        });

        /**
         * Global shortcut sample definition
         */
        i(GlobalShortcutRegister.class).put(Shortcut.getShortcut(true, Character.valueOf('S')),
                new AbstractExtendedAction() {
                    public void actionPerformed(final ActionEvent event) {
                        NotifyUser.info("Global Ctrl+S pressed");
                    }
                });

        // And because nobody use this module, we get the class (to force the
        // creation of the Helloworld instance):
        // i(HelloWorld.class);

        // See KuneEntryPoint for a sample of modules registration
    }
}