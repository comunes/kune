package org.ourproject.kune.platf.server.tool;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.google.inject.Singleton;

@Singleton
public class ToolRegistry {
    private final ConcurrentHashMap<String, ServerTool> tools;

    public ToolRegistry() {
	this.tools = new ConcurrentHashMap<String, ServerTool>();
    }

    public void register(final ServerTool tool) {
	tools.put(tool.getName(), tool);
    }

    public Collection<ServerTool> all() {
	return tools.values();
    }

}
