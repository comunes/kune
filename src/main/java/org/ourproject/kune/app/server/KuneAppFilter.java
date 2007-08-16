package org.ourproject.kune.app.server;

import org.ourproject.kune.app.server.servlet.ApplicationBuilder;
import org.ourproject.kune.app.server.servlet.GWTFilter;

import com.google.inject.Scopes;

public class KuneAppFilter extends GWTFilter {

    @Override
    protected void configure(final ApplicationBuilder builder) {
	KuneApp kune = new KuneApp("development", "kune.properties", Scopes.SINGLETON);
	kune.configure(builder);
    }
}
