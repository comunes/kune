package cc.kune.core.client;

import org.ourproject.common.client.CommonGinModule;

import cc.kune.core.client.ws.CorePresenter;
import cc.kune.core.client.ws.CoreView;

import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;

/**
 * The Kune Core EventBus.
 */
@Events(startView = CoreView.class, ginModules = { CoreGinModule.class, CommonGinModule.class })
@Debug
public interface CoreEventBus extends EventBus {

    /**
     * Application start event
     */
    @Start
    @Event(handlers = CorePresenter.class)
    void start();

}
