/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.server.manager.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import magick.MagickException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * If you get a UnsatisfiedLinkError this is a problem with jmagick installation
 * (in debian, apt-get install libjmagick6-jni, and add
 * LD_LIBRARY_PATH=/usr/lib/jni/ to this test environment params or sudo ln -s
 * /usr/lib/jni/libJMagick.so /usr/lib/libJMagick.so)
 * 
 * See the output of:
 * System.out.println(System.getProperty("java.library.path")); to see when is
 * expecting the .so/.dll
 */
public class ImageUtilsDefaultTest {

    private static final String IMG_PATH = "src/test/java/org/ourproject/kune/platf/server/manager/file/";
    private static String[] images = { "orig.png", "orig.gif", "orig.jpg", "orig.tiff", "orig.pdf" };
    private static String imageDest;
    private static String pdf = "orig.pdf";

    @AfterClass
    public static void after() {
        File file = new File(imageDest);
        file.delete();
    }

    @BeforeClass
    public static void before() {
        imageDest = IMG_PATH + "output.png";
    }

    @Test
    public void convertPdfToPng() throws MagickException {
        ImageUtilsDefault.createThumbFromPdf(IMG_PATH + pdf, imageDest);
    }

    @Test
    public void generateIcon() throws MagickException, FileNotFoundException {
        for (String image : images) {
            ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 16, 16);
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(16, (int) dimension.getHeight());
            assertEquals(16, (int) dimension.getWidth());
        }
    }

    @Test
    public void generateThumb() throws MagickException, FileNotFoundException {
        for (String image : images) {
            ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 100, 85);
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(85, (int) dimension.getHeight());
            assertEquals(85, (int) dimension.getWidth());
        }
    }

    @Test
    public void testProportionalHigher() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(500, 1000, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(200, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherLikeSamples() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(1200, 1600, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(133, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(16, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherSame() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(20, 100, 100);
        assertEquals(20, proportionalDim.width);
        assertEquals(100, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalHigherSmaller() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(20, 10, 100);
        assertEquals(20, proportionalDim.width);
        assertEquals(10, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalToBiggerNormal() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 300, false);
        assertEquals(300, proportionalDim.width);
        assertEquals(150, proportionalDim.height);
    }

    @Test
    public void testProportionalToBiggerSame() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 600, false);
        assertEquals(600, proportionalDim.width);
        assertEquals(300, proportionalDim.height);
    }

    @Test
    public void testProportionalToBiggerSmaller() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 700, false);
        assertEquals(600, proportionalDim.width);
        assertEquals(300, proportionalDim.height);
    }

    @Test
    public void testProportionalToBiggerWider() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(300, 600, 200, false);
        assertEquals(100, proportionalDim.width);
        assertEquals(200, proportionalDim.height);
    }

    @Test
    public void testProportionalWider() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(1000, 500, 100);
        assertEquals(200, proportionalDim.width);
        assertEquals(100, proportionalDim.height);
        assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalWiderSame() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(100, 20, 100);
        assertEquals(100, proportionalDim.width);
        assertEquals(20, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testProportionalWiderSmaller() {
        Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(5, 10, 100);
        assertEquals(5, proportionalDim.width);
        assertEquals(10, proportionalDim.height);
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
        assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
    }

    @Test
    public void testResize() throws MagickException, FileNotFoundException {
        for (String image : images) {
            assertTrue(ImageUtilsDefault.scaleImage(IMG_PATH + image, imageDest, 100, 100));
            Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
            assertEquals(100, (int) dimension.getHeight());
            assertEquals(100, (int) dimension.getWidth());
        }
    }

    @Test
    public void testSize() throws MagickException {
        for (String image : images) {
            if (!image.equals(pdf)) {
                Dimension dimension = ImageUtilsDefault.getDimension(IMG_PATH + image);
                assertEquals(400, (int) dimension.getHeight());
                assertEquals(300, (int) dimension.getWidth());
            }
        }
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void thumbSmallerThanCropMustFail() throws MagickException, FileNotFoundException {
        ImageUtilsDefault.createThumb(IMG_PATH + images[0], imageDest, 100, 200);
    }
}
