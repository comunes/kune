package org.ourproject.kune.workspace.client.entitylogo;

public interface EntityLogo {

    /**
     * Refresh the logo from the group info in the client session
     */
    void refreshGroupLogo();

    /**
     * Reload the logo from the group reloaded from the server
     */
    void reloadGroupLogo();

}