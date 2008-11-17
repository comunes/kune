package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.actions.ContentIconsRegistry;
import org.ourproject.kune.platf.client.registry.AclEditableRegistry;
import org.ourproject.kune.platf.client.registry.AuthorableRegistry;
import org.ourproject.kune.platf.client.registry.CanBeHomepageRegistry;
import org.ourproject.kune.platf.client.registry.ComentableRegistry;
import org.ourproject.kune.platf.client.registry.ContentCapabilitiesRegistry;
import org.ourproject.kune.platf.client.registry.DragableRegistry;
import org.ourproject.kune.platf.client.registry.DropableRegistry;
import org.ourproject.kune.platf.client.registry.EmailSubscribeAbleRegistry;
import org.ourproject.kune.platf.client.registry.LicensableRegistry;
import org.ourproject.kune.platf.client.registry.PublishModerableRegistry;
import org.ourproject.kune.platf.client.registry.RateableRegistry;
import org.ourproject.kune.platf.client.registry.RenamableRegistry;
import org.ourproject.kune.platf.client.registry.TageableRegistry;
import org.ourproject.kune.platf.client.registry.TranslatableRegistry;
import org.ourproject.kune.platf.client.registry.VersionableRegistry;
import org.ourproject.kune.platf.client.registry.XmppComentableRegistry;
import org.ourproject.kune.platf.client.registry.XmppNotifyCapableRegistry;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class KuneRegistryModule extends AbstractModule {

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

        register(Singleton.class, new Factory<ContentIconsRegistry>(ContentIconsRegistry.class) {
            @Override
            public ContentIconsRegistry create() {
                return new ContentIconsRegistry();
            }
        });

        register(Singleton.class, new Factory<ContentCapabilitiesRegistry>(ContentCapabilitiesRegistry.class) {
            @Override
            public ContentCapabilitiesRegistry create() {
                return new ContentCapabilitiesRegistry($(AuthorableRegistry.class), $(AclEditableRegistry.class),
                        $(ContentIconsRegistry.class), $(CanBeHomepageRegistry.class), $(ComentableRegistry.class),
                        $(DragableRegistry.class), $(DropableRegistry.class), $(EmailSubscribeAbleRegistry.class),
                        $(LicensableRegistry.class), $(PublishModerableRegistry.class), $(RateableRegistry.class),
                        $(TageableRegistry.class), $(RenamableRegistry.class), $(TranslatableRegistry.class),
                        $(VersionableRegistry.class), $(XmppComentableRegistry.class),
                        $(XmppNotifyCapableRegistry.class));
            }
        });

    }

}
