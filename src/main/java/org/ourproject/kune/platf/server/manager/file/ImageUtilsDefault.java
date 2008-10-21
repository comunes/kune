package org.ourproject.kune.platf.server.manager.file;

import java.awt.Dimension;
import java.awt.Rectangle;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

public class ImageUtilsDefault {

    /**
     * http://en.wikipedia.org/wiki/Thumbnail
     * 
     */
    public static void createThumb(String fileOrig, String fileDest, int cropDimension) throws MagickException {
        createThumb(fileOrig, fileDest, cropDimension, cropDimension);
    }

    public static void createThumb(String fileOrig, String fileDest, int thumbDimension, int cropDimension)
            throws MagickException {
        if (thumbDimension < cropDimension) {
            throw new IndexOutOfBoundsException("Thumb dimension must be bigger than crop dimension");
        }
        MagickImage imageOrig = createImage(fileOrig);
        Dimension origDimension = imageOrig.getDimension();
        int origHeight = origDimension.height;
        int origWidth = origDimension.width;
        Dimension proportionalDim = calculatePropDim(origWidth, origHeight, thumbDimension, true);
        MagickImage scaled = scaleImage(imageOrig, proportionalDim.width, proportionalDim.height);
        int x = calculateCenteredCoordinate(proportionalDim.width, cropDimension);
        int y = calculateCenteredCoordinate(proportionalDim.height, cropDimension);
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

    public static boolean scaleImageToMax(String fileOrig, String fileDest, int maxSize) throws MagickException {
        MagickImage imageOrig = createImage(fileOrig);
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
