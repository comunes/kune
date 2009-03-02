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
package org.ourproject.kune.workspace.client.rate;

import org.ourproject.kune.platf.client.services.Images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class Star {
    private static final Images img = Images.App.getInstance();
    public static final int GREY = 0;
    public static final int YELLOW = 1;

    public final static Star[] CLEAR = { new Star(), new Star(), new Star(), new Star(), new Star() };

    private AbstractImagePrototype image;

    public Star() {
        image = img.starGrey();
    }

    public AbstractImagePrototype getImage() {
        return image;
    }

    public Star(final double rateDecimals) {
        if (rateDecimals == 1) {
            image = img.starYellow();
        } else if (rateDecimals == 0) {
            image = img.starGrey();
        } else {
            final int rateTrucated = (int) rateDecimals;
            final int rateDecimal = (int) ((rateDecimals - rateTrucated) * 10) * 10;
            if (rateDecimal == 10) {
                image = img.star10();
            } else if (rateDecimal == 20) {
                image = img.star20();
            } else if (rateDecimal == 30) {
                image = img.star30();
            } else if (rateDecimal == 40) {
                image = img.star40();
            } else if (rateDecimal == 50) {
                image = img.star50();
            } else if (rateDecimal == 60) {
                image = img.star60();
            } else if (rateDecimal == 70) {
                image = img.star70();
            } else if (rateDecimal == 80) {
                image = img.star80();
            } else if (rateDecimal == 90) {
                image = img.star90();
            }
        }
    }

    public static Star[] genStars(final double rate) {
        Star[] stars;
        stars = new Star[5];
        final int rateTruncated = (int) rate;
        final double rateDecimals = rate - rateTruncated;

        for (int i = 0; i < 5; i++) {
            if (i < rateTruncated) {
                stars[i] = new Star(Star.YELLOW);
            } else {
                if (i == rateTruncated) {
                    stars[i] = new Star(rateDecimals);
                } else {
                    stars[i] = new Star(Star.GREY);
                }
            }
        }
        return stars;
    }
}
