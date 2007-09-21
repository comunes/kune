package org.ourproject.kune.workspace.client;

public interface WorkspaceEvents {
    public static final String START_APP = "plaft.StartApplication";
    public static final String STOP_APP = "platf.StopApplication";
    public static final String USER_LOGGED_IN = "ws.UserLoggedIn";
    public static final String USER_LOGGED_OUT = "ws.UserLoggedOut";
    public static final String INIT_DATA_RECEIVED = "ws.InitDataReceived";
    public static final String REQ_JOIN_GROUP = "ws.RequestJoinGroup";
    public static final String DEL_MEMBER = "ws.DeleteMember";
    public static final String GOTO_GROUP = "ws.GotoGroup";
    public static final String DENY_JOIN_GROUP = "ws.DenyJoinGroup";
    public static final String ACCEPT_JOIN_GROUP = "ws.AcceptJoinGroup";
}
