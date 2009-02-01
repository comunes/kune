/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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
