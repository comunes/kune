package org.ourproject.kune.platf.server.manager.impl;

import java.awt.Dimension;
import java.awt.Rectangle;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageUtilsDefault {

    /**
     * http://en.wikipedia.org/wiki/Thumbnail
     */
    public static void createThumb(String fileOrig, String fileDest, int thumbDimension, int cropDimension)
            throws MagickException {
        if (thumbDimension < cropDimension) {
            throw new IndexOutOfBoundsException("Thumb dimension must be bigger than crop dimension");
        }
        MagickImage imageOrig = createImage(fileOrig);
        Dimension origDimension = imageOrig.getDimension();
        int origHeight = origDimension.height;
        int origWidth = origDimension.width;
        Dimension proportionalDim = calculateProportionalDim(origWidth, origHeight, thumbDimension);
        int x = calculateCenteredCoordinate(proportionalDim.width, cropDimension);
        int y = calculateCenteredCoordinate(proportionalDim.height, cropDimension);
        MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);
        cropImage(scaled, fileDest, x, y, cropDimension, cropDimension);
    }

    public static boolean cropImage(String fileOrig, String fileDest, int x, int y, int width, int height)
            throws MagickException {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return cropImage(fileOrig, fileDest, rectangle);
    }

    public static boolean cropImage(String fileOrig, String fileDest, Rectangle rectangle) throws MagickException {
        return cropImage(createImage(fileOrig), fileDest, rectangle);
    }

    public static Dimension getDimension(String file) throws MagickException {
        MagickImage imageOrig = createImage(file);
        return imageOrig.getDimension();
    }

    public static boolean scaleImage(String fileOrig, String fileDest, Dimension dimension) throws MagickException {
        MagickImage imageOrig = createImage(fileOrig);
        return scaleImage(imageOrig, fileDest, (int) dimension.getWidth(), (int) dimension.getHeight());
    }

    public static boolean scaleImage(String fileOrig, String fileDest, int width, int height) throws MagickException {
        MagickImage imageOrig = createImage(fileOrig);
        return scaleImage(imageOrig, fileDest, width, height);
    }

    static int calculateCenteredCoordinate(int size, int crop) {
        int i = (size - crop) / 2;
        return i < 0 ? 0 : i;
    }

    static Dimension calculateProportionalDim(int origWidth, int origHeight, int maxSize) {
        boolean higher = origHeight > origWidth;
        double height = higher ? (origHeight * maxSize / origWidth) : maxSize;
        double width = !higher ? (origWidth * maxSize / origHeight) : maxSize;
        if ((higher && origHeight <= maxSize) || (!higher && origWidth <= maxSize)) {
            return new Dimension(origWidth, origHeight);
        }
        return new Dimension((int) width, (int) height);
    }

    private static ImageInfo createEmptyImageInfo() throws MagickException {
        ImageInfo info = new ImageInfo();
        return info;
    }

    private static MagickImage createImage(String file) throws MagickException {
        return new MagickImage(new ImageInfo(file));
    }

    private static boolean cropImage(MagickImage fileOrig, String fileDest, int x, int y, int width, int height)
            throws MagickException {
        Rectangle rectangle = new Rectangle(x, y, width, height);
        return cropImage(fileOrig, fileDest, rectangle);
    }

    private static boolean cropImage(MagickImage fileOrig, String fileDest, Rectangle rectangle) throws MagickException {
        MagickImage cropped = fileOrig.cropImage(rectangle);
        return writeImage(cropped, fileDest);
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
