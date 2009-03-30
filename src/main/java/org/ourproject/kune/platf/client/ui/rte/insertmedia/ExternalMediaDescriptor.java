package org.ourproject.kune.platf.client.ui.rte.insertmedia;

public class ExternalMediaDescriptor {

    private final String name;
    private final String siteurl;
    private final String detectRegex;
    private final String idRegex;
    private final String embedTemplate;

    public ExternalMediaDescriptor(final String name, final String siteurl, final String detectRegex,
            final String idRegex, final String embedTemplate) {
        this.name = name;
        this.siteurl = siteurl;
        this.detectRegex = detectRegex;
        this.idRegex = idRegex;
        this.embedTemplate = embedTemplate;
    }

    public String getEmbed(final String url) {
        String id = url.replaceFirst(idRegex, "kk");
        return embedTemplate.replaceFirst("\\[%d\\]", id);
    }

    public boolean is(final String url) {
        return url.matches(detectRegex);
    }

    public String getName() {
        return name;
    }

    public String getSiteurl() {
        return siteurl;
    }
}
