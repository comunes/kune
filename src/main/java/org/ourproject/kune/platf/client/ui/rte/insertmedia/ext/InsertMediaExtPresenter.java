package org.ourproject.kune.platf.client.ui.rte.insertmedia.ext;

import org.ourproject.kune.platf.client.ui.noti.NotifyUser.Level;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaDescriptor;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.ExternalMediaRegistry;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia.InsertMediaAbstractPresenter;

public class InsertMediaExtPresenter extends InsertMediaAbstractPresenter implements InsertMediaExt {

    private InsertMediaExtView view;
    private final ExternalMediaRegistry externalMediaRegistry;
    private ExternalMediaDescriptor mediaDescriptor;

    public InsertMediaExtPresenter(final InsertMediaDialog insertMediaDialog,
            final ExternalMediaRegistry externalMediaRegistry) {
        super(insertMediaDialog);
        this.externalMediaRegistry = externalMediaRegistry;

        externalMediaRegistry.add(new ExternalMediaDescriptor(
                "youtube",
                "http://youtube.com",
                ".*youtube.*",
                ".*youtube.com/watch.*[\\?&]v=(.*)",
                "<object width=\"###WIDTH###\" height=\"###HEIGHT###\"><param name=\"movie\" value=\"http://youtube.com/v/###URL###\" /><param name=\"wmode\" value=\"opaque\" /><embed src=\"http://youtube.com/v/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" wmode=\"opaque\"></embed></object>",
                480, 385));
        externalMediaRegistry.add(new ExternalMediaDescriptor(
                "blip.tv",
                "http://blip.tv",
                ".*blip\\.tv.*",
                ".*blip.tv/file/([0-9]+).*",
                "<embed src=\"http://blip.tv/play/###URL###\" type=\"application/x-shockwave-flash\" width=\"###WIDTH###\" height=\"###HEIGHT###\" allowscriptaccess=\"always\" allowfullscreen=\"true\" wmode=\"opaque\" ></embed>",
                480, 385));
        externalMediaRegistry.add(new ExternalMediaDescriptor(
                "google video",
                "http://video.google.com/",
                ".*video\\.google.*",
                ".*video\\.google\\.com/videoplay.*docid=([0-9]+).*",
                "<embed id=\"VideoPlayback\" src=\"http://video.google.com/googleplayer.swf?docid=###URL###&hl=es&fs=true\" style=\"width:###WIDTH###;height:###HEIGHT###\" allowFullScreen=\"true\" allowScriptAccess=\"always\" type=\"application/x-shockwave-flash\" wmode=\"opaque\" > </embed>",
                400, 326));
    }

    public void init(final InsertMediaExtView view) {
        super.init(view);
        this.view = view;
    }

    @Override
    public boolean isValid() {
        String url = view.getSrc();
        mediaDescriptor = externalMediaRegistry.get(url);
        if (mediaDescriptor.equals(ExternalMediaRegistry.NO_MEDIA)) {
            insertMediaDialog.setErrorMessage("We cannot process this video link", Level.error);
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected String updateMediaInfo() {
        return mediaDescriptor.getEmbed(view.getSrc());
    }
}
