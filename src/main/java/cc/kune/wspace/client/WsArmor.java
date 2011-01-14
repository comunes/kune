package cc.kune.wspace.client;

import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public interface WsArmor {

    ForIsWidget getDocFooter();

    ForIsWidget getDocHeader();

    ForIsWidget getDocSubheader();

    ForIsWidget getEntityFooter();

    ForIsWidget getEntityHeader();

    ForIsWidget getEntityToolsCenter();

    ForIsWidget getEntityToolsNorth();

    ForIsWidget getEntityToolsSouth();

    ForIsWidget getSitebar();

    void selectHomeSpace();

    void selectUserSpace();

    void selectGroupSpace();

    void selectPublicSpace();

}