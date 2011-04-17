package cc.kune.gspace.client.tool;

import cc.kune.core.shared.dto.HasContent;

public interface ContentViewer {

    void attach();

    void detach();

    void setContent(HasContent state);
}
