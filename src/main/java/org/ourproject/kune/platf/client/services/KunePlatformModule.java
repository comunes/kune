package org.ourproject.kune.platf.client.services;

import org.ourproject.kune.platf.client.state.Session;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalette;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePanel;
import org.ourproject.kune.platf.client.ui.palette.ColorWebSafePalettePresenter;
import org.ourproject.kune.platf.client.ui.upload.FileUploader;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderDialog;
import org.ourproject.kune.platf.client.ui.upload.FileUploaderPresenter;
import org.ourproject.kune.workspace.client.ctxnav.ContextNavigator;
import org.ourproject.kune.workspace.client.i18n.I18nUITranslationService;
import org.ourproject.kune.workspace.client.skel.WorkspaceSkeleton;

import com.calclab.suco.client.ioc.decorator.Singleton;
import com.calclab.suco.client.ioc.module.AbstractModule;
import com.calclab.suco.client.ioc.module.Factory;

public class KunePlatformModule extends AbstractModule {

    @Override
    protected void onLoad() {
	register(Singleton.class, new Factory<ColorWebSafePalette>(ColorWebSafePalette.class) {
	    public ColorWebSafePalette create() {
		final ColorWebSafePalettePresenter presenter = new ColorWebSafePalettePresenter();
		final ColorWebSafePalettePanel panel = new ColorWebSafePalettePanel(presenter);
		presenter.init(panel);
		return presenter;
	    }
	});

	register(Singleton.class, new Factory<FileUploader>(FileUploader.class) {
	    public FileUploader create() {
		final FileUploaderPresenter presenter = new FileUploaderPresenter($(Session.class),
			$$(ContextNavigator.class));
		final FileUploaderDialog panel = new FileUploaderDialog(presenter, $(I18nUITranslationService.class),
			$(WorkspaceSkeleton.class));
		presenter.init(panel);
		return presenter;
	    }
	});

    }
}
