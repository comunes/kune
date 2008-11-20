package org.ourproject.kune.platf.client.dto;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TagCloudResultDTO implements IsSerializable {
    private List<TagCountDTO> tagCountList;
    private int maxValue;
    private int minValue;

    public TagCloudResultDTO() {
        this(null, 0, 0);
    }

    public TagCloudResultDTO(List<TagCountDTO> tagCountList, int maxValue, int minValue) {
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

    public List<TagCountDTO> getTagCountList() {
        return tagCountList;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setTagCountList(List<TagCountDTO> tagCountList) {
        this.tagCountList = tagCountList;
    }

    @Override
    public String toString() {
        return "TagCloudResultDTO[list:" + tagCountList + "; maxValue: " + maxValue + "; minValue: " + minValue + "]";
    }
}
