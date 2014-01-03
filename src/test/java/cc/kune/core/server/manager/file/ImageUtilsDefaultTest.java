/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.core.server.manager.file;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;

import magick.MagickException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

// TODO: Auto-generated Javadoc
/**
 * If you get a UnsatisfiedLinkError this is a problem with jmagick installation
 * (in debian, apt-get install libjmagick6-jni, and add
 * LD_LIBRARY_PATH=/usr/lib/jni/ to this test environment params or sudo ln -s
 * /usr/lib/jni/libJMagick.so /usr/lib/libJMagick.so)
 * 
 * See the output of:
 * System.out.println(System.getProperty("java.library.path")); to see when is
 * expecting the .so/.dll
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class ImageUtilsDefaultTest {

  /** The image dest. */
  private static String imageDest;

  /** The images. */
  private static String[] images = { "orig.png", "orig.gif", "orig.jpg", "orig.tiff", "orig.pdf" };

  /** The Constant IMG_PATH. */
  private static final String IMG_PATH = "src/test/java/cc/kune/core/server/manager/file/";

  /** The pdf. */
  private static String pdf = "orig.pdf";

  /**
   * After.
   */
  @AfterClass
  public static void after() {
    final File file = new File(imageDest);
    file.delete();
  }

  /**
   * Before.
   */
  @BeforeClass
  public static void before() {
    imageDest = IMG_PATH + "output.png";
  }

  /**
   * Convert pdf to png.
   * 
   * @throws MagickException
   *           the magick exception
   */
  @Ignore
  @Test
  public void convertPdfToPng() throws MagickException {
    ImageUtilsDefault.createThumbFromPdf(IMG_PATH + pdf, imageDest);
  }

  /**
   * Generate icon.
   * 
   * @throws MagickException
   *           the magick exception
   * @throws FileNotFoundException
   *           the file not found exception
   */
  @Ignore
  @Test
  public void generateIcon() throws MagickException, FileNotFoundException {
    for (final String image : images) {
      ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 16, 16);
      final Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
      assertEquals(16, (int) dimension.getHeight());
      assertEquals(16, (int) dimension.getWidth());
    }
  }

  /**
   * Generate thumb.
   * 
   * @throws MagickException
   *           the magick exception
   * @throws FileNotFoundException
   *           the file not found exception
   */
  @Ignore
  @Test
  public void generateThumb() throws MagickException, FileNotFoundException {
    for (final String image : images) {
      ImageUtilsDefault.createThumb(IMG_PATH + image, imageDest, 100, 85);
      final Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
      assertEquals(85, (int) dimension.getHeight());
      assertEquals(85, (int) dimension.getWidth());
    }
  }

  /**
   * Test proportional higher.
   */
  @Test
  public void testProportionalHigher() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(500, 1000, 100);
    assertEquals(100, proportionalDim.width);
    assertEquals(200, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional higher like samples.
   */
  @Test
  public void testProportionalHigherLikeSamples() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(1200, 1600, 100);
    assertEquals(100, proportionalDim.width);
    assertEquals(133, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(16, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional higher same.
   */
  @Test
  public void testProportionalHigherSame() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(20, 100, 100);
    assertEquals(20, proportionalDim.width);
    assertEquals(100, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional higher smaller.
   */
  @Test
  public void testProportionalHigherSmaller() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(20, 10, 100);
    assertEquals(20, proportionalDim.width);
    assertEquals(10, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional to bigger normal.
   */
  @Test
  public void testProportionalToBiggerNormal() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 300, false);
    assertEquals(300, proportionalDim.width);
    assertEquals(150, proportionalDim.height);
  }

  /**
   * Test proportional to bigger same.
   */
  @Test
  public void testProportionalToBiggerSame() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 600, false);
    assertEquals(600, proportionalDim.width);
    assertEquals(300, proportionalDim.height);
  }

  /**
   * Test proportional to bigger smaller.
   */
  @Test
  public void testProportionalToBiggerSmaller() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(600, 300, 700, false);
    assertEquals(600, proportionalDim.width);
    assertEquals(300, proportionalDim.height);
  }

  /**
   * Test proportional to bigger wider.
   */
  @Test
  public void testProportionalToBiggerWider() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(300, 600, 200, false);
    assertEquals(100, proportionalDim.width);
    assertEquals(200, proportionalDim.height);
  }

  /**
   * Test proportional wider.
   */
  @Test
  public void testProportionalWider() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(1000, 500, 100);
    assertEquals(200, proportionalDim.width);
    assertEquals(100, proportionalDim.height);
    assertEquals(50, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional wider same.
   */
  @Test
  public void testProportionalWiderSame() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(100, 20, 100);
    assertEquals(100, proportionalDim.width);
    assertEquals(20, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test proportional wider smaller.
   */
  @Test
  public void testProportionalWiderSmaller() {
    final Dimension proportionalDim = ImageUtilsDefault.calculatePropDim(5, 10, 100);
    assertEquals(5, proportionalDim.width);
    assertEquals(10, proportionalDim.height);
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.width, 100));
    assertEquals(0, ImageUtilsDefault.calculateCenteredCoordinate(proportionalDim.height, 100));
  }

  /**
   * Test resize.
   * 
   * @throws MagickException
   *           the magick exception
   * @throws FileNotFoundException
   *           the file not found exception
   */
  @Ignore
  @Test
  public void testResize() throws MagickException, FileNotFoundException {
    for (final String image : images) {
      assertTrue(ImageUtilsDefault.scaleImage(IMG_PATH + image, imageDest, 100, 100));
      final Dimension dimension = ImageUtilsDefault.getDimension(imageDest);
      assertEquals(100, (int) dimension.getHeight());
      assertEquals(100, (int) dimension.getWidth());
    }
  }

  /**
   * Test size.
   * 
   * @throws MagickException
   *           the magick exception
   */
  @Test
  public void testSize() throws MagickException {
    for (final String image : images) {
      if (!image.equals(pdf)) {
        final Dimension dimension = ImageUtilsDefault.getDimension(IMG_PATH + image);
        assertEquals(400, (int) dimension.getHeight());
        assertEquals(300, (int) dimension.getWidth());
      }
    }
  }

  /**
   * Thumb smaller than crop must fail.
   * 
   * @throws MagickException
   *           the magick exception
   * @throws FileNotFoundException
   *           the file not found exception
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void thumbSmallerThanCropMustFail() throws MagickException, FileNotFoundException {
    ImageUtilsDefault.createThumb(IMG_PATH + images[0], imageDest, 100, 200);
  }
}
