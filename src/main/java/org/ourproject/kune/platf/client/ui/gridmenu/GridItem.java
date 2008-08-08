package org.ourproject.kune.platf.client.ui.gridmenu;


public class GridItem<T> {

    private GridGroup group;
    private String id;
    private String iconHtml;
    private String title;
    private String titleHtml;
    private String endIconHtml;
    private String tooltip;
    private String tooltipTitle;
    private T item;
    private CustomMenu<T> menu;

    public GridItem(final T item, final GridGroup group, final String id, final String iconHtml, final String title,
	    final String titleHtml, final String endIconHtml, final String tooltipTitle, final String tooltip,
	    final CustomMenu<T> menu) {
	this.item = item;
	this.group = group;
	this.id = id;
	this.iconHtml = iconHtml;
	this.title = title;
	this.titleHtml = titleHtml;
	this.endIconHtml = endIconHtml;
	this.tooltip = tooltip;
	this.tooltipTitle = tooltipTitle;
	this.menu = menu;
    }

    public String getEndIconHtml() {
	return endIconHtml;
    }

    public GridGroup getGroup() {
	return group;
    }

    public String getIconHtml() {
	return iconHtml;
    }

    public String getId() {
	return id;
    }

    public T getItem() {
	return item;
    }

    public CustomMenu<T> getMenu() {
	return menu;
    }

    public String getTitle() {
	return title;
    }

    public String getTitleHtml() {
	return titleHtml;
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

    public void setGroup(final GridGroup group) {
	this.group = group;
    }

    public void setIconHtml(final String iconHtml) {
	this.iconHtml = iconHtml;
    }

    public void setId(final String id) {
	this.id = id;
    }

    public void setItem(final T item) {
	this.item = item;
    }

    public void setMenu(final CustomMenu<T> menu) {
	this.menu = menu;
    }

    public void setTitle(final String title) {
	this.title = title;
    }

    public void setTitleHtml(final String titleHtml) {
	this.titleHtml = titleHtml;
    }

    public void setTooltip(final String tooltip) {
	this.tooltip = tooltip;
    }

    public void setTooltipTitle(final String tooltipTitle) {
	this.tooltipTitle = tooltipTitle;
    }

}
