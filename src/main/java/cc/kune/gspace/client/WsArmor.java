package cc.kune.gspace.client;

import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;

public interface WsArmor {

    ForIsWidget getDocContainer();

    ForIsWidget getDocFooter();

    ForIsWidget getDocHeader();

    ForIsWidget getDocSubheader();

    ForIsWidget getEntityFooter();

    ForIsWidget getEntityHeader();

    ForIsWidget getEntityToolsCenter();

    ForIsWidget getEntityToolsNorth();

    ForIsWidget getEntityToolsSouth();

    ForIsWidget getSitebar();

    void selectGroupSpace();

    void selectHomeSpace();

    void selectPublicSpace();

    void selectUserSpace();

}