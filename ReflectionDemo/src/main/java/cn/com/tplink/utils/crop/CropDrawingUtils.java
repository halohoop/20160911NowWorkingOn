
package cn.com.tplink.utils.crop;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class CropDrawingUtils {

    public static void drawCropRect(Canvas canvas, RectF bounds) {
        if(bounds.isEmpty()){
            return;
        }
        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(3);
        int cropLineColor = 0xFF00FF00;
        p.setColor(cropLineColor);

        canvas.drawRect(bounds, p);

        float top = bounds.top;
        float left = bounds.left;
        float right = bounds.right;
        float bottom = bounds.bottom;
        float width = right - left;
        float height = bottom - top;
        float conerSize = Math.min(10, Math.min(width, height) / 5);
        p.setStrokeWidth(6);
        // left-top corner
        canvas.drawPoint(left, top, p);
        canvas.drawLine(left, top, left + conerSize, top, p);
        canvas.drawLine(left, top, left, top + conerSize, p);
        // right-top corner
        canvas.drawPoint(right, top, p);
        canvas.drawLine(right, top, right - conerSize, top, p);
        canvas.drawLine(right, top, right, top + conerSize, p);
        // left-bottom corner
        canvas.drawPoint(left, bottom, p);
        canvas.drawLine(left, bottom, left + conerSize, bottom, p);
        canvas.drawLine(left, bottom, left, bottom - conerSize, p);
        // right-bottom corner
        canvas.drawPoint(right, bottom, p);
        canvas.drawLine(right, bottom, right - conerSize, bottom, p);
        canvas.drawLine(right, bottom, right, bottom - conerSize, p);
    }

    public static void drawShadows(Canvas canvas, Paint p, RectF innerBounds, RectF outerBounds) {
        if(innerBounds.isEmpty()){
            canvas.drawRect(outerBounds, p);
            return;
        }
        canvas.drawRect(outerBounds.left, outerBounds.top, innerBounds.right, innerBounds.top, p);
        canvas.drawRect(innerBounds.right, outerBounds.top, outerBounds.right, innerBounds.bottom,
                p);
        canvas.drawRect(innerBounds.left, innerBounds.bottom, outerBounds.right,
                outerBounds.bottom, p);
        canvas.drawRect(outerBounds.left, innerBounds.top, innerBounds.left, outerBounds.bottom, p);
    }

}
