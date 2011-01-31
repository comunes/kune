package cc.kune.pspace.client;


import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class PSpaceGinModule extends AbstractPresenterModule {

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gwt.inject.client.AbstractGinModule#configure()
     */
    @Override
    protected void configure() {
        bindPresenter(PSpacePresenter.class, PSpacePresenter.PSpaceView.class, PSpacePanel.class,
                PSpacePresenter.PSpaceProxy.class);
    }

}