package org.ourproject.kune.platf.client;

import java.util.ArrayList;

import org.ourproject.kune.platf.client.actions.EventDispatcher;
import org.ourproject.kune.platf.client.extend.ContentProviderAsync;
import org.ourproject.kune.platf.client.extend.Register;
import org.ourproject.kune.platf.client.extend.ViewFactory;

import com.google.gwt.user.client.History;

public class KunePlatform implements Register {
    private ArrayList tools;
    public final EventDispatcher dispatcher;

    public KunePlatform() {
        this.tools = new ArrayList();
        dispatcher = new EventDispatcher();
        History.addHistoryListener(dispatcher);
    }
    
    public void registerTool(String name, ContentProviderAsync provider, ViewFactory factory) {
        tools.add(new Tool(name, provider, factory));
    }

    public Tool getTool(int index) {
        return (Tool) tools.get(index);
    }

    public int getToolCount() {
        return tools.size();
    }

}
