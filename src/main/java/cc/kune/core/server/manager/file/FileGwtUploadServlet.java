package cc.kune.core.server.manager.file;

import javax.inject.Inject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.utils.StateToken;

public class FileGwtUploadServlet extends FileGwtUploadAbstractServlet {

  public static final Log LOG = LogFactory.getLog(FileGwtUploadServlet.class);
  private static final long serialVersionUID = 1L;

  @Inject
  public FileGwtUploadServlet(final KuneProperties kuneProperties) {
    super(kuneProperties);
  }

  @Override
  protected String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId) {
    LOG.info("ok");
    return "";
  }

}
