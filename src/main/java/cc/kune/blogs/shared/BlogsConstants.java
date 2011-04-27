package cc.kune.blogs.shared;

import cc.kune.core.shared.ToolConstants;

public final class BlogsConstants {

    public static final String NAME = "blogs";
    public static final String TYPE_BLOG = NAME + "." + "blog";
    public static final String TYPE_POST = NAME + "." + "post";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + ToolConstants.UPLOADEDFILE_SUFFIX;

    private BlogsConstants() {
    }
}
