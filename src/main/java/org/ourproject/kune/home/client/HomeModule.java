package org.ourproject.kune.home.client;

import org.ourproject.kune.home.client.rpc.HomeService;
import org.ourproject.kune.platf.client.extend.ClientModule;
import org.ourproject.kune.platf.client.extend.Register;


public class HomeModule implements ClientModule{
    public void configure(Register register) {
        register.registerTool("home", HomeService.App.getInstance(), new HomeViewFactory());
    }
}
