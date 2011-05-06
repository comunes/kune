package cc.kune.core.server.manager.file;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cc.kune.core.client.services.FileConstants;
import cc.kune.core.server.properties.KuneProperties;
import cc.kune.core.shared.domain.utils.StateToken;

public abstract class FileGwtUploadAbstractServlet extends UploadAction {

  public static final Log LOG = LogFactory.getLog(FileGwtUploadAbstractServlet.class);
  private static final long serialVersionUID = 1L;

  public FileGwtUploadAbstractServlet(final KuneProperties kuneProperties) {
    this.maxSize = Integer.valueOf(kuneProperties.get(KuneProperties.UPLOAD_MAX_FILE_SIZE_IN_KS));
    this.uploadDelay = Integer.valueOf(kuneProperties.get(KuneProperties.UPLOAD_DELAY_FOR_TEST));
  }

  protected abstract String createUploadedFile(final String userHash, final StateToken stateToken,
      final String fileName, final FileItem file, final String typeId);

  @Override
  public String executeAction(final HttpServletRequest request, final List<FileItem> fileItems)
      throws UploadActionException {
    String userHash = null;
    StateToken stateToken = null;
    String typeId = null;
    String fileName = null;
    FileItem file = null;
    for (final Object element : fileItems) {
      final FileItem item = (FileItem) element;
      if (item.isFormField()) {
        final String name = item.getFieldName();
        final String value = item.getString();
        LOG.info("name: " + name + " value: " + value);
        if (name.equals(FileConstants.HASH)) {
          userHash = value;
        }
        if (name.equals(FileConstants.TOKEN)) {
          stateToken = new StateToken(value);
        }
        if (name.equals(FileConstants.TYPE_ID)) {
          typeId = value;
        }
      } else {
        fileName = item.getName();
        LOG.info("file: " + fileName + " fieldName: " + item.getFieldName() + " size: " + item.getSize()
            + " typeId: " + typeId);
        file = item;
      }
    }

    return createUploadedFile(userHash, stateToken, fileName, file, typeId);

  }

  protected void logFileDel(final boolean delResult) {
    if (!delResult) {
      LOG.error("Cannot delete file");
    }
  }

}
