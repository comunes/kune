package org.ourproject.kune.app.server.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ApplicationListener {
    void onApplicationStart(HttpServletRequest request, HttpServletResponse response);
}
