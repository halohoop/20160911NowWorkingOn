
package cn.com.tplink.utils.crop;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CropView extends View {

    public static final String LOGTAG = "CropView";

    private CropObject mCropObj;

    private float mPrevX = 0;

    private float mPrevY = 0;

    private boolean mMovingBlock = false;

    private Paint mPaint = new Paint();

    private Handler mHandler;

    private static final int MSG_MOVE_DONE = 1;

    private static final int MSG_CONFIGURE_CHANGED= 3;

    private static final long CONFIGURE_CHANGED_DELAY = 1000;

    private enum Mode {
        NONE, MOVE
    }

    private Mode mState = Mode.NONE;

    private int mOverlayShadowColor = 0x4C000000;

    public CropView(Context context) {
        super(context);
    }

    public CropView(Context context, AttributeSet attr) {
        super(context, attr);
    }

    public CropView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
    }

    public RectF getCrop(){
        return mCropObj.getInnerBounds();
    }

    public void initialize(RectF cropBounds, RectF imageBounds) {
        mCropObj = new CropObject(imageBounds, cropBounds, 0);
    }

    public void setHandler(Handler handler){
        mHandler = handler;
    }

    public void resetOuterBounds(int left, int top, int right, int bottom){
        RectF inner = mCropObj.getInnerBounds();
        RectF outer = new RectF(left, top, right, bottom);
        mCropObj.resetBoundsTo(inner, outer);
    }

    public void resetInnerBounds(int left, int top, int right, int bottom){
        RectF inner = new RectF(left, top, right, bottom);
        resetInnerBounds(inner);
    }

    public void resetInnerBounds(RectF inner){
        if(inner == null){
            inner = new RectF();
        }
        RectF outer = mCropObj.getOuterBounds();
        mCropObj.resetBoundsTo(inner, outer);
        invalidate();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        rotateBounds();
        if(mHandler != null){
            mHandler.sendEmptyMessageDelayed(MSG_CONFIGURE_CHANGED, CONFIGURE_CHANGED_DELAY);
        }
    }

    private void rotateBounds(){
        RectF inner = mCropObj.getInnerBounds();
        CropMath.rotateXY(inner);
        RectF outer = mCropObj.getOuterBounds();
        CropMath.rotateXY(outer);
        mCropObj.resetBoundsTo(inner, outer);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isFocusable()){
            return false;
        }
        if(getCrop().isEmpty()){
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case (MotionEvent.ACTION_DOWN):
                if (mState == Mode.NONE) {
                    if (!mCropObj.selectEdge(x, y)) {
                        mMovingBlock = mCropObj.selectEdge(CropObject.MOVE_BLOCK);
                    }
                    mPrevX = x;
                    mPrevY = y;
                    mState = Mode.MOVE;
                }
                break;
            case (MotionEvent.ACTION_UP):
                if (mState == Mode.MOVE) {
                    mCropObj.selectEdge(CropObject.MOVE_NONE);
                    mMovingBlock = false;
                    mPrevX = x;
                    mPrevY = y;
                    mState = Mode.NONE;
                    // Tell the observer we've done
                    if(mHandler != null){
                        mHandler.sendEmptyMessage(MSG_MOVE_DONE);
                    }
                }
                break;
            case (MotionEvent.ACTION_MOVE):
                if (mState == Mode.MOVE) {
                    float dx = x - mPrevX;
                    float dy = y - mPrevY;
                    mCropObj.moveCurrentSelection(dx, dy);
                    mPrevX = x;
                    mPrevY = y;
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF inner = mCropObj.getInnerBounds();
        RectF outer = mCropObj.getOuterBounds();

        mPaint.setColor(mOverlayShadowColor);
        mPaint.setStyle(Paint.Style.FILL);

        CropDrawingUtils.drawShadows(canvas, mPaint, inner , outer);
        CropDrawingUtils.drawCropRect(canvas, inner);
    }
}
