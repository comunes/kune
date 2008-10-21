package org.ourproject.kune.platf.server.manager.file;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ourproject.kune.platf.client.dto.StateToken;
import org.ourproject.kune.platf.client.ui.download.FileParams;
import org.ourproject.kune.platf.server.domain.Group;
import org.ourproject.kune.platf.server.manager.GroupManager;

import com.google.inject.Inject;

public class EntityLogoDownloadManager extends FileDownloadManagerAbstract {

    private static final long serialVersionUID = 1L;

    @Inject
    GroupManager groupManager;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException,
            IOException {

        // final String userHash = req.getParameter(FileParams.HASH);
        final StateToken stateToken = new StateToken(req.getParameter(FileParams.TOKEN));

        Group group = groupManager.findByShortName(stateToken.getGroup());

        if (group == Group.NO_GROUP) {
            throw new ServletException("Group not found trying to get the logo");
        }

        if (!group.hasLogo()) {
            throw new ServletException("This Group has no logo");
        }

        byte[] logo = group.getLogo();

        resp.setContentLength(logo.length);
        resp.setContentType(group.getLogoMime().toString());
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + group.getShortName() + "-logo\"");
        resp.getOutputStream().write(logo);
    }
}
