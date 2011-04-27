package cc.kune.docs.shared;

import cc.kune.core.shared.ToolConstants;

public final class DocsConstants {

    public static final String NAME = "docs";
    public static final String ROOT_NAME = "documents";
    public static final String TYPE_DOCUMENT = NAME + "." + "doc";
    public static final String TYPE_FOLDER = NAME + "." + "folder";
    public static final String TYPE_ROOT = NAME + "." + "root";
    public static final String TYPE_UPLOADEDFILE = NAME + "." + ToolConstants.UPLOADEDFILE_SUFFIX;

    // @Deprecated
    // public static final String TYPE_WAVE = NAME + "." +
    // ToolConstants.WAVE_SUFFIX;

    private DocsConstants() {
    }
}
