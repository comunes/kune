package org.ourproject.kune.platf.server.tool;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Singleton;

@Singleton
public class ToolRegistry {
    static Log log = LogFactory.getLog(ToolRegistry.class);

    // TODO: estamos seguros de esto?
    private final ConcurrentHashMap<String, ServerTool> tools;

    public ToolRegistry() {
	this.tools = new ConcurrentHashMap<String, ServerTool>();
    }

    public void register(final ServerTool tool) {
	log.debug("Registering tool: " + tool.getName());
	tools.put(tool.getName(), tool);
    }

    public Collection<ServerTool> all() {
	return tools.values();
    }

    public ServerTool get(final String toolName) {
	return tools.get(toolName);

    }

}
