package cn.com.tplink.utils.crop;

import android.graphics.Bitmap;
import android.graphics.RectF;

public class CropMath {

    /**
     * Gets a float array of the 2D coordinates representing a rectangles
     * corners.
     * The order of the corners in the float array is:
     * 0------->1
     * ^        |
     * |        v
     * 3<-------2
     *
     * @param r  the rectangle to get the corners of
     * @return  the float array of corners (8 floats)
     */

    public static float[] getCornersFromRect(RectF r) {
        float[] corners = {
                r.left, r.top,
                r.right, r.top,
                r.right, r.bottom,
                r.left, r.bottom
        };
        return corners;
    }

    /**
     * Returns true iff point (x, y) is within or on the rectangle's bounds.
     * RectF's "contains" function treats points on the bottom and right bound
     * as not being contained.
     *
     * @param r the rectangle
     * @param x the x value of the point
     * @param y the y value of the point
     * @return
     */
    public static boolean inclusiveContains(RectF r, float x, float y) {
        return !(x > r.right || x < r.left || y > r.bottom || y < r.top);
    }

    /**
     * Takes an array of 2D coordinates representing corners and returns the
     * smallest rectangle containing those coordinates.
     *
     * @param array array of 2D coordinates
     * @return smallest rectangle containing coordinates
     */
    public static RectF trapToRect(float[] array) {
        RectF r = new RectF(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY,
                Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        for (int i = 1; i < array.length; i += 2) {
            float x = array[i - 1];
            float y = array[i];
            r.left = (x < r.left) ? x : r.left;
            r.top = (y < r.top) ? y : r.top;
            r.right = (x > r.right) ? x : r.right;
            r.bottom = (y > r.bottom) ? y : r.bottom;
        }
        r.sort();
        return r;
    }

    /**
     * If edge point [x, y] in array [x0, y0, x1, y1, ...] is outside of the
     * image bound rectangle, clamps it to the edge of the rectangle.
     *
     * @param imageBound the rectangle to clamp edge points to.
     * @param array an array of points to clamp to the rectangle, gets set to
     *            the clamped values.
     */
    public static void getEdgePoints(RectF imageBound, float[] array) {
        if (array.length < 2)
            return;
        for (int x = 0; x < array.length; x += 2) {
            array[x] = GeometryMathUtils.clamp(array[x], imageBound.left, imageBound.right);
            array[x + 1] = GeometryMathUtils.clamp(array[x + 1], imageBound.top, imageBound.bottom);
        }
    }

    /**
     * Takes a point and the corners of a rectangle and returns the two corners
     * representing the side of the rectangle closest to the point.
     *
     * @param point the point which is being checked
     * @param corners the corners of the rectangle
     * @return two corners representing the side of the rectangle
     */
    public static float[] closestSide(float[] point, float[] corners) {
        int len = corners.length;
        float oldMag = Float.POSITIVE_INFINITY;
        float[] bestLine = null;
        for (int i = 0; i < len; i += 2) {
            float[] line = {
                    corners[i], corners[(i + 1) % len],
                    corners[(i + 2) % len], corners[(i + 3) % len]
            };
            float mag = GeometryMathUtils.vectorLength(
                    GeometryMathUtils.shortestVectorFromPointToLine(point, line));
            if (mag < oldMag) {
                oldMag = mag;
                bestLine = line;
            }
        }
        return bestLine;
    }


    /**
     * Returns the size of a bitmap in bytes.
     * @param bmap  bitmap whose size to check
     * @return  bitmap size in bytes
     */
    public static int getBitmapSize(Bitmap bmap) {
        return bmap.getRowBytes() * bmap.getHeight();
    }

    /**
     * Swaps a rectangle's (x,y) coordinates
     *
     * @param r
     */
    public static void rotateXY(RectF r) {
        float swap;
        swap = r.top;
        r.top = r.left;
        r.left = swap;
        swap = r.bottom;
        r.bottom = r.right;
        r.right = swap;
    }

}
