
package org.ourproject.kune.workspace.client;

public interface WorkspaceEvents {
    public static final String START_APP = "platf.StartApplication";
    public static final String STOP_APP = "platf.StopApplication";
    public static final String USER_LOGGED_IN = "ws.UserLoggedIn";
    public static final String USER_LOGGED_OUT = "ws.UserLoggedOut";
    public static final String ONLY_CHECK_USER_SESSION = "ws_OnlyCheckUserSession";
    public static final String INIT_DATA_RECEIVED = "ws.InitDataReceived";
    public static final String REQ_JOIN_GROUP = "ws.RequestJoinGroup";
    public static final String DEL_MEMBER = "ws.DeleteMember";
    public static final String DENY_JOIN_GROUP = "ws.DenyJoinGroup";
    public static final String ACCEPT_JOIN_GROUP = "ws.AcceptJoinGroup";
    public static final String SET_COLLAB_AS_ADMIN = "ws.SetMemberAsAsmin";
    public static final String SET_ADMIN_AS_COLLAB = "ws.SetMemberAsCollab";
    public static final String ADD_ADMIN_MEMBER = "ws.AddAdminMember";
    public static final String ADD_COLLAB_MEMBER = "ws.AddCollabMember";
    public static final String ADD_VIEWER_MEMBER = "ws.AddViewerMember";
    public static final String UNJOIN_GROUP = "ws.UnJoinGroup";
    public static final String CHANGE_GROUP_WSTHEME = "ws.ChangeGroupWsTheme";
    public static final String RATE_CONTENT = "ws.RateContent";
    public static final String ENABLE_RATEIT = "ws.EnableRateIt";
    public static final String DISABLE_RATEIT = "ws.DisableRateIt";
    public static final String ADD_MEMBER_GROUPLIVESEARCH = "ws.AddMemberGroupLiveSearch";
    public static final String ADD_USERLIVESEARCH = "ws.AddUserLiveSearch";
    public static final String GET_LEXICON = "i18n.getLexicon";
    public static final String SHOW_TRANSLATOR = "i18n.ShowTranslator";
    public static final String SHOW_SEARCHER = "ws.ShowSearcher";
    public static final String GET_TRANSLATION = "i18n.GetTranslation";
    public static final String DO_TRANSLATION = "i18n.doTranslation";
    public static final String USER_LOGIN = "ws.UserLogin";
    public static final String USER_LOGOUT = "ws.UserLogout";
    public static final String USER_REGISTER = "ws.UserRegister";
    public static final String CREATE_NEW_GROUP = "ws.CreateNewGroup";
    public static final String RELOAD_CONTEXT = "ws.ReoloadContext";
    public static final String WS_SPLITTER_STARTRESIZING = "ws.SplitterStartResizing";
    public static final String WS_SPLITTER_STOPRESIZING = "ws.SplitterStopResizing";
    public static final String RECALCULATE_WORKSPACE_SIZE = "ws.RecalculateWorkspaceSize";
}
