package cc.kune.gspace.client.options.logo;

import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploader.OnCancelUploaderHandler;
import gwtupload.client.IUploader.OnChangeUploaderHandler;
import gwtupload.client.IUploader.OnFinishUploaderHandler;
import gwtupload.client.IUploader.OnStartUploaderHandler;
import gwtupload.client.MultiUploader;
import cc.kune.core.client.services.FileConstants;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Label;

public class EntityUploaderForm extends Composite {

  private final Label dialogInfoLabel;
  private final Hidden tokenField;
  private final MultiUploader uploader;
  private final Hidden userhashField;

  public EntityUploaderForm(final String servlet, final String btnText) {
    final EntityOptionsUploadButton btn = new EntityOptionsUploadButton(btnText);
    uploader = new MultiUploader(FileInputType.CUSTOM.with(btn));
    uploader.setServletPath(servlet);
    uploader.setMaximumFiles(1);
    dialogInfoLabel = new Label();
    dialogInfoLabel.setWordWrap(true);
    dialogInfoLabel.addStyleName("kune-Margin-20-tb");
    uploader.setValidExtensions("png", "jpg", "gif", "jpeg", "bmp");

    userhashField = new Hidden(FileConstants.HASH, FileConstants.HASH);
    tokenField = new Hidden(FileConstants.TOKEN, FileConstants.TOKEN);

    final FlowPanel holder = new FlowPanel();

    uploader.add(userhashField);
    uploader.add(tokenField);
    holder.add(dialogInfoLabel);
    holder.add(uploader);
    initWidget(holder);
  }

  public HandlerRegistration addOnCancelUploadHandler(final OnCancelUploaderHandler handler) {
    return uploader.addOnCancelUploadHandler(handler);
  }

  public HandlerRegistration addOnChangeUploadHandler(final OnChangeUploaderHandler handler) {
    return uploader.addOnChangeUploadHandler(handler);
  }

  public HandlerRegistration addOnFinishUploadHandler(final OnFinishUploaderHandler handler) {
    return uploader.addOnFinishUploadHandler(handler);
  }

  public HandlerRegistration addOnStartUploadHandler(final OnStartUploaderHandler handler) {
    return uploader.addOnStartUploadHandler(handler);
  }

  public void reset() {
    uploader.reset();
  }

  public void setLabelText(final String text) {
    dialogInfoLabel.setText(text);
  }

  public void setUploadParams(final String userHash, final String token) {
    userhashField.setValue(userHash);
    tokenField.setValue(token);
  }

}
