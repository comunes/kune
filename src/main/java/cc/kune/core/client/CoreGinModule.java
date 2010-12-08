package cc.kune.core.client;

import org.ourproject.kune.ws.armor.client.Body;
import org.ourproject.kune.ws.armor.client.IBody;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

/**
 * The Class Core GinModule.
 */
public class CoreGinModule extends AbstractGinModule {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        bind(IBody.class).to(Body.class).in(Singleton.class);
    }

}
