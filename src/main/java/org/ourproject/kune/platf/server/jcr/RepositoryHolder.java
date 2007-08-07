package org.ourproject.kune.platf.server.jcr;

import java.util.Hashtable;

import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.jackrabbit.core.jndi.RegistryHelper;

public class RepositoryHolder {
    private static Repository repository;

    public static Repository getRepository() {
	return repository;
    }

    public static void openRepository(final String name, final String configFilePath, final String homeDir) {
	if (repository == null) {
	    createRepository(name, configFilePath, homeDir);
	} else {
	    // throw new RuntimeException("repository already opened!.");
	}
    }

    private static void createRepository(final String name, final String configFilePath, final String homeDir) {
	try {
	    Hashtable<String, String> env = new Hashtable<String, String>();
	    env.put(Context.INITIAL_CONTEXT_FACTORY,
		    "org.apache.jackrabbit.core.jndi.provider.DummyInitialContextFactory");
	    env.put(Context.PROVIDER_URL, "localhost");
	    InitialContext ctx;
	    ctx = new InitialContext(env);
	    RegistryHelper.registerRepository(ctx, name, configFilePath, homeDir, true);
	    repository = (Repository) ctx.lookup(name);
	} catch (NamingException e) {
	    throw new RuntimeException("error creating repository", e);
	} catch (RepositoryException e) {
	    throw new RuntimeException("error creating repository", e);
	}
    }

    public static void closeRepository() {
	// repository = null;
    }
}
