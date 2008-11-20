package org.ourproject.kune.platf.server.domain;

import java.util.List;

public class TagCloudResult {
    private List<TagCount> tagCountList;
    private int maxValue;
    private int minValue;

    public TagCloudResult(List<TagCount> tagCountList, int maxValue, int minValue) {
        this.tagCountList = tagCountList;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public List<TagCount> getTagCountList() {
        return tagCountList;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setTagCountList(List<TagCount> tagCountList) {
        this.tagCountList = tagCountList;
    }

    @Override
    public String toString() {
        return "TagCloudResult[list:" + tagCountList + "; maxValue: " + maxValue + "; minValue: " + minValue + "]";
    }
}
