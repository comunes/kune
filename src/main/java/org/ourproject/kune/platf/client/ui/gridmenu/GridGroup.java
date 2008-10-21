package org.ourproject.kune.platf.client.ui.gridmenu;

public class GridGroup {

    public static GridGroup NoGridGroup = new GridGroup("", "", "", "", false);

    private String name;
    private String tooltipTitle;
    private String tooltip;
    private String endIconHtml;

    public GridGroup(final String adminsTitle, final String tooltipTitle, final String tooltip,
            final boolean countVisible) {
        this(adminsTitle, tooltipTitle, tooltip, "", countVisible);
    }

    public GridGroup(final String name, final String tooltipTitle, final String tooltip, final String endIconHtml,
            final boolean countVisible) {
        this.name = name;
        this.tooltipTitle = tooltipTitle;
        this.tooltip = tooltip;
        this.endIconHtml = endIconHtml;
    }

    GridGroup(final String adminsTitle) {
        this(adminsTitle, "", "", false);
    }

    GridGroup(final String adminsTitle, final String tooltip) {
        this(adminsTitle, "", tooltip, false);
    }

    GridGroup(final String adminsTitle, final String tooltipTitle, final String tooltip) {
        this(adminsTitle, tooltipTitle, tooltip, false);
    }

    public String getEndIconHtml() {
        return endIconHtml;
    }

    public String getName() {
        return name;
    }

    public String getTooltip() {
        return tooltip;
    }

    public String getTooltipTitle() {
        return tooltipTitle;
    }

    public void setEndIconHtml(final String endIconHtml) {
        this.endIconHtml = endIconHtml;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setTooltip(final String tooltip) {
        this.tooltip = tooltip;
    }

    public void setTooltipTitle(final String tooltipTitle) {
        this.tooltipTitle = tooltipTitle;
    }

}
