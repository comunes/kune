package org.ourproject.kune.blogs.client.actions;

public interface BlogsEvents {
    public static final String ADD_DOCUMENT = "docs.addDocument";
    public static final String ADD_FOLDER = "docs.AddFolder";
    public static final String GO_PARENT_FOLDER = "platf.GoParentFolder";
    public static final String SAVE_DOCUMENT = "docs.SaveDocument";
    public static final String ADD_AUTHOR = "docs.addAuthor";
    public static final String REMOVE_AUTHOR = "docs.removeAuthor";
    public static final String SET_LANGUAGE = "docs.setLanguage";
    public static final String SET_PUBLISHED_ON = "docs.setPublishedOn";
    public static final String SET_TAGS = "docs.setTags";
    public static final String RENAME_CONTENT = "docs.setTitle";
    public static final String RENAME_TOKEN = "docs.RenameToken";
    public static final String DEL_CONTENT = "docs.delContent";
}
