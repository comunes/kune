package cc.kune.common.shared.ui;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UploadFile implements IsSerializable {

  public static UploadFile build(final String name, final String type, final Long size, final String data) {
    return new UploadFile(name, type, size, data);
  }

  private String data;
  private String name;
  private Long size;
  private String type;

  public UploadFile() {
  }

  public UploadFile(final String name, final String type, final Long size, final String data) {
    this.name = name;
    this.type = type;
    this.size = size;
    this.data = data;
  }

  public String getData() {
    return data;
  }

  public String getName() {
    return name;
  }

  public Long getSize() {
    return size;
  }

  public String getType() {
    return type;
  }

  public void setData(final String data) {
    this.data = data;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public void setSize(final Long size) {
    this.size = size;
  }

  public void setType(final String type) {
    this.type = type;
  }

}
