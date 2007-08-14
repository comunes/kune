package org.ourproject.kune.app.server;

import org.ourproject.kune.app.server.servlet.ApplicationBuilder;
import org.ourproject.kune.app.server.servlet.GWTFilter;

import com.google.inject.servlet.ServletScopes;

public class KuneAppFilter extends GWTFilter {

    @Override
    protected void configure(final ApplicationBuilder builder) {
	KuneApp kune = new KuneApp("development", "kune.properties", ServletScopes.SESSION);
	kune.configure(builder);
    }
}
