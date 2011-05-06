/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.workspace.client;

import org.ourproject.kune.platf.client.services.AbstractExtendedModule;

import cc.kune.core.client.registry.AclEditableRegistry;
import cc.kune.core.client.registry.AuthorableRegistry;
import cc.kune.core.client.registry.CanBeHomepageRegistry;
import cc.kune.core.client.registry.ComentableRegistry;
import cc.kune.core.client.registry.DragableRegistry;
import cc.kune.core.client.registry.DropableRegistry;
import cc.kune.core.client.registry.EmailSubscribeAbleRegistry;
import cc.kune.core.client.registry.LicensableRegistry;
import cc.kune.core.client.registry.PublishModerableRegistry;
import cc.kune.core.client.registry.RateableRegistry;
import cc.kune.core.client.registry.RenamableRegistry;
import cc.kune.core.client.registry.TageableRegistry;
import cc.kune.core.client.registry.TranslatableRegistry;
import cc.kune.core.client.registry.VersionableRegistry;
import cc.kune.core.client.registry.XmppComentableRegistry;
import cc.kune.core.client.registry.XmppNotifyCapableRegistry;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.Factory;

public class RegistryModule extends AbstractExtendedModule {

  @Override
  protected void onInstall() {

    register(Singleton.class, new Factory<AclEditableRegistry>(AclEditableRegistry.class) {
      @Override
      public AclEditableRegistry create() {
        return new AclEditableRegistry();
      }
    });
    register(Singleton.class, new Factory<AuthorableRegistry>(AuthorableRegistry.class) {
      @Override
      public AuthorableRegistry create() {
        return new AuthorableRegistry();
      }
    });
    register(Singleton.class, new Factory<CanBeHomepageRegistry>(CanBeHomepageRegistry.class) {
      @Override
      public CanBeHomepageRegistry create() {
        return new CanBeHomepageRegistry();
      }
    });

    register(Singleton.class, new Factory<ComentableRegistry>(ComentableRegistry.class) {
      @Override
      public ComentableRegistry create() {
        return new ComentableRegistry();
      }
    });

    register(Singleton.class, new Factory<DragableRegistry>(DragableRegistry.class) {
      @Override
      public DragableRegistry create() {
        return new DragableRegistry();
      }
    });

    register(Singleton.class, new Factory<DropableRegistry>(DropableRegistry.class) {
      @Override
      public DropableRegistry create() {
        return new DropableRegistry();
      }
    });

    register(Singleton.class, new Factory<EmailSubscribeAbleRegistry>(EmailSubscribeAbleRegistry.class) {
      @Override
      public EmailSubscribeAbleRegistry create() {
        return new EmailSubscribeAbleRegistry();
      }
    });

    register(Singleton.class, new Factory<LicensableRegistry>(LicensableRegistry.class) {
      @Override
      public LicensableRegistry create() {
        return new LicensableRegistry();
      }
    });

    register(Singleton.class, new Factory<PublishModerableRegistry>(PublishModerableRegistry.class) {
      @Override
      public PublishModerableRegistry create() {
        return new PublishModerableRegistry();
      }
    });

    register(Singleton.class, new Factory<RateableRegistry>(RateableRegistry.class) {
      @Override
      public RateableRegistry create() {
        return new RateableRegistry();
      }
    });

    register(Singleton.class, new Factory<TageableRegistry>(TageableRegistry.class) {
      @Override
      public TageableRegistry create() {
        return new TageableRegistry();
      }
    });

    register(Singleton.class, new Factory<RenamableRegistry>(RenamableRegistry.class) {
      @Override
      public RenamableRegistry create() {
        return new RenamableRegistry();
      }
    });

    register(Singleton.class, new Factory<TranslatableRegistry>(TranslatableRegistry.class) {
      @Override
      public TranslatableRegistry create() {
        return new TranslatableRegistry();
      }
    });

    register(Singleton.class, new Factory<VersionableRegistry>(VersionableRegistry.class) {
      @Override
      public VersionableRegistry create() {
        return new VersionableRegistry();
      }
    });

    register(Singleton.class, new Factory<XmppComentableRegistry>(XmppComentableRegistry.class) {
      @Override
      public XmppComentableRegistry create() {
        return new XmppComentableRegistry();
      }
    });

    register(Singleton.class, new Factory<XmppNotifyCapableRegistry>(XmppNotifyCapableRegistry.class) {
      @Override
      public XmppNotifyCapableRegistry create() {
        return new XmppNotifyCapableRegistry();
      }
    });

    // register(Singleton.class, new
    // Factory<IconsRegistry>(IconsRegistry.class) {
    // @Override
    // public IconsRegistry create() {
    // return new IconsRegistry();
    // }
    // });

    // register(Singleton.class, new
    // Factory<ContentCapabilitiesRegistry>(ContentCapabilitiesRegistry.class)
    // {
    // @Override
    // public ContentCapabilitiesRegistry create() {
    // return new ContentCapabilitiesRegistry(i(AuthorableRegistry.class),
    // i(AclEditableRegistry.class),
    // i(OldIconsRegistry.class), i(CanBeHomepageRegistry.class),
    // i(ComentableRegistry.class),
    // i(DragableRegistry.class), i(DropableRegistry.class),
    // i(EmailSubscribeAbleRegistry.class),
    // i(LicensableRegistry.class), i(PublishModerableRegistry.class),
    // i(RateableRegistry.class),
    // i(TageableRegistry.class), i(RenamableRegistry.class),
    // i(TranslatableRegistry.class),
    // i(VersionableRegistry.class), i(XmppComentableRegistry.class),
    // i(XmppNotifyCapableRegistry.class));
    // }
    // });
    //
    // register(Singleton.class, new
    // Factory<ContextActionRegistry>(ContextActionRegistry.class) {
    // @Override
    // public ContextActionRegistry create() {
    // return new ContextActionRegistry();
    // }
    // });
    //
    // register(Singleton.class, new
    // Factory<ContentActionRegistry>(ContentActionRegistry.class) {
    // @Override
    // public ContentActionRegistry create() {
    // return new ContentActionRegistry();
    // }
    // });
    //
    // register(Singleton.class, new
    // Factory<GroupActionRegistry>(GroupActionRegistry.class) {
    // @Override
    // public GroupActionRegistry create() {
    // return new GroupActionRegistry();
    // }
    // });
    //
    // register(Singleton.class, new
    // Factory<UserActionRegistry>(UserActionRegistry.class) {
    // @Override
    // public UserActionRegistry create() {
    // return new UserActionRegistry();
    // }
    // });
  }
}
