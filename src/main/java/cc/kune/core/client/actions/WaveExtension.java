package cc.kune.core.client.actions;

public class WaveExtension {
  public static class Builder {
    private String gadgetUrl;
    private String iconCss;
    private String iconUrl;
    private String installerUrl;
    private String name;

    public Builder() {
    }

    public WaveExtension build() {
      return new WaveExtension(this);
    }

    public Builder gadgetUrl(final String gadgetUrl) {
      this.gadgetUrl = gadgetUrl;
      return this;
    }

    public Builder iconCss(final String iconCss) {
      this.iconCss = iconCss;
      return this;
    }

    public Builder iconUrl(final String iconUrl) {
      this.iconUrl = iconUrl;
      return this;
    }

    public Builder installerUrl(final String installerUrl) {
      this.installerUrl = installerUrl;
      return this;
    }

    public Builder name(final String name) {
      this.name = name;
      return this;
    }
  }

  private final String gadgetUrl;
  private final String iconCss;
  private final String iconUrl;
  private final String installerUrl;
  private final String name;

  public WaveExtension(final Builder builder) {
    gadgetUrl = builder.gadgetUrl;
    iconCss = builder.iconCss;
    iconUrl = builder.iconUrl;
    installerUrl = builder.installerUrl;
    name = builder.name;
  }

  public String getGadgetUrl() {
    return gadgetUrl;
  }

  public String getIconCss() {
    return iconCss;
  }

  public String getIconUrl() {
    return iconUrl;
  }

  public String getInstallerUrl() {
    return installerUrl;
  }

  public String getName() {
    return name;
  }

}
