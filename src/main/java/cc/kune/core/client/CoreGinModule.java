package cc.kune.core.client;

import org.ourproject.kune.ws.armor.client.Body;
import org.ourproject.kune.ws.armor.client.IBody;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

public class CoreGinModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(IBody.class).to(Body.class).in(Singleton.class);
    }

}
