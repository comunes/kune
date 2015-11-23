package org.ourproject.massmob.client;

import org.ourproject.massmob.client.ui.MassmobMainPanel;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(MassmobGinModule.class)
public interface MassmobGinInjector extends Ginjector {
    MassmobMainPanel getWaveGadget();
}
