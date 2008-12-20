/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 */package org.ourproject.kune.platf.server.manager.file;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageUtilsDefault {

    /**
     * http://en.wikipedia.org/wiki/Thumbnail
     * 
     * @throws FileNotFoundException
     * 
     */
    public static void createThumb(String fileOrig, String fileDest, int cropDimension) throws MagickException,
            FileNotFoundException {
        createThumb(fileOrig, fileDest, cropDimension, cropDimension);
    }

    public static void createThumb(String fileOrig, String fileDest, int thumbDimension, int cropDimension)
            throws MagickException, FileNotFoundException {
        checkExist(fileOrig);
        if (thumbDimension < cropDimension) {
            throw new IndexOutOfBoundsException("Thumb dimension must be bigger than crop dimension");
        }
        MagickImage imageOrig = readImage(fileOrig);
        Dimension origDimension = imageOrig.getDimension();
        int origHeight = origDimension.height;
        int origWidth = origDimension.width;
        Dimension proportionalDim = calculatePropDim(origWidth, origHeight, thumbDimension, true);
        MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);
        int x = calculateCenteredCoordinate(proportionalDim.width, cropDimension);
        int y = calculateCenteredCoordinate(proportionalDim.height, cropDimension);
        cropImage(scaled, fileDest, x, y, cropDimension, cropDimension);
    }

    /**
     * convert -density 300 -quality 100 -resize 720x file.pdf result.png
     * 
     * @param pdfFile
     * @param newPngFile
     * @return
     * @throws MagickException
     */
    static public boolean createThumbFromPdf(String pdfFile, String newPngFile) throws MagickException {
        MagickImage pdf = readImage(pdfFile);
        MagickImage pdf1 = pdf.breakFrames()[0];
        return writeImage(pdf1, newPngFile);
    }

    public static boolean cropImage(String fileOrig, String fileDest, int x, int y, int width, int height)
            throws MagickException, FileNotFoundException {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return cropImage(fileOrig, fileDest, rectangle);
    }

    public static boolean cropImage(String fileOrig, String fileDest, Rectangle rectangle) throws MagickException,
            FileNotFoundException {
        checkExist(fileOrig);
        return cropImage(readImage(fileOrig), fileDest, rectangle);
    }

    public static Dimension getDimension(String file) throws MagickException {
        MagickImage imageOrig = readImage(file);
        return imageOrig.getDimension();
    }

    /**
     * FIXME: Not working, returns null always (bug)
     * 
     */
    public static String getPage(String file) throws MagickException {
        ImageInfo imageInfo = new ImageInfo(file);
        new MagickImage(imageInfo);
        return imageInfo.getPage();
    }

    public static boolean scaleImage(String fileOrig, String fileDest, Dimension dimension) throws MagickException,
            FileNotFoundException {
        checkExist(fileOrig);
        MagickImage imageOrig = readImage(fileOrig);
        return scaleImage(imageOrig, fileDest, (int) dimension.getWidth(), (int) dimension.getHeight());
    }

    public static boolean scaleImage(String fileOrig, String fileDest, int width, int height) throws MagickException,
            FileNotFoundException {
        checkExist(fileOrig);
        MagickImage imageOrig = readImage(fileOrig);
        return scaleImage(imageOrig, fileDest, width, height);
    }

    public static boolean scaleImageToMax(String fileOrig, String fileDest, int maxSize) throws MagickException,
            FileNotFoundException {
        checkExist(fileOrig);
        MagickImage imageOrig = readImage(fileOrig);
        Dimension origDimension = imageOrig.getDimension();
        int origHeight = origDimension.height;
        int origWidth = origDimension.width;
        Dimension proportionalDim = calculatePropDim(origWidth, origHeight, maxSize, false);
        MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);

        return writeImage(scaled, fileDest);
    }

    static int calculateCenteredCoordinate(int size, int crop) {
        int i = (size - crop) / 2;
        return i < 0 ? 0 : i;
    }

    static Dimension calculatePropDim(int origWidth, int origHeight, int maxSize) {
        return calculatePropDim(origWidth, origHeight, maxSize, true);
    }

    static Dimension calculatePropDim(int origWidth, int origHeight, int maxSize, boolean toShortest) {
        boolean higher = origHeight > origWidth;
        int propHeight = origHeight * maxSize / origWidth;
        int propWidth = origWidth * maxSize / origHeight;
        double height = toShortest ? (higher ? propHeight : maxSize) : (higher ? maxSize : propHeight);
        double width = toShortest ? (!higher ? propWidth : maxSize) : (!higher ? maxSize : propWidth);
        if ((higher && origHeight <= maxSize) || (!higher && origWidth <= maxSize)) {
            return new Dimension(origWidth, origHeight);
        }
        return new Dimension((int) width, (int) height);
    }

    private static void checkExist(String fileOrig) throws FileNotFoundException {
        File file = new File(fileOrig);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
    }

    private static ImageInfo createEmptyImageInfo() throws MagickException {
        ImageInfo imageInfo = new ImageInfo();
        return imageInfo;
    }

    private static ImageInfo createEmptyImageInfoWithNoPage() throws MagickException {
        ImageInfo imageInfo = createEmptyImageInfo();
        imageInfo.setPage("0x0+0+0");
        return imageInfo;
    }

    private static boolean cropImage(MagickImage fileOrig, String fileDest, int x, int y, int width, int height)
            throws MagickException {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return cropImage(fileOrig, fileDest, rectangle);
    }

    private static boolean cropImage(MagickImage fileOrig, String fileDest, Rectangle rectangle) throws MagickException {
        MagickImage cropped = fileOrig.cropImage(rectangle);
        cropped.setFileName(fileDest);
        ImageInfo imageInfo = createEmptyImageInfoWithNoPage();
        return cropped.writeImage(imageInfo);
    }

    private static MagickImage readImage(String file) throws MagickException {
        ImageInfo imageInfo = new ImageInfo(file);
        return new MagickImage(imageInfo);
    }

    private static MagickImage scaleImage(MagickImage imageOrig, int width, int height) throws MagickException {
        return imageOrig.scaleImage(width, height);
    }

    private static boolean scaleImage(MagickImage imageOrig, String fileDest, int width, int height)
            throws MagickException {
        MagickImage imageDest = scaleImage(imageOrig, width, height);
        return writeImage(imageDest, fileDest);
    }

    private static boolean writeImage(MagickImage imageDest, String fileDest) throws MagickException {
        imageDest.setFileName(fileDest);
        return imageDest.writeImage(createEmptyImageInfo());
    }
}
