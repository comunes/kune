package cc.kune.wspace.client;

import org.ourproject.common.client.actions.ui.IsActionExtensible;

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

    IsActionExtensible getSiteActionsToolbar();

    ForIsWidget getSitebar();

    void selectGroupSpace();

    void selectHomeSpace();

    void selectPublicSpace();

    void selectUserSpace();

}