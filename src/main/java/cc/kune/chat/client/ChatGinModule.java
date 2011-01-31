package cc.kune.chat.client;

import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ChatGinModule extends AbstractPresenterModule {
    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        bind(ChatClient.class).to(ChatClientDefault.class).in(Singleton.class);
    }
}