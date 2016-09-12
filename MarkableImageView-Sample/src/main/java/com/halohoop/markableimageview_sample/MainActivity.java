package com.halohoop.markableimageview_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.halohoop.markableimageview.shape.Shape;
import com.halohoop.markableimageview.views.MarkableImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "halohoop";
    private MarkableImageView mMiv;
    private ImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMiv = (MarkableImageView) findViewById(R.id.miv);
        mIv = (ImageView) findViewById(R.id.iv);
    }

    public void enterEditMode(View v) {
        Log.i(TAG, "enter");
        mMiv.enterEditMode();
    }

    public void exitEditMode(View v) {
        Log.i(TAG, "exit");
        mMiv.exitEditMode();
    }

    public void addArrow(View v) {
        Log.i(TAG, "addArrow");
        mMiv.setNowAddingShapeType(Shape.ShapeType.ARROW);
    }

    public void addCircle(View v) {
        Log.i(TAG, "addCircle");
        mMiv.setNowAddingShapeType(Shape.ShapeType.CIRCLE);
    }

    public void addRectangle(View v) {
        Log.i(TAG, "addRectangle");
        mMiv.setNowAddingShapeType(Shape.ShapeType.RECTANGLE);
    }

    public void saveFile(View v) {
        Log.i(TAG, "saveFile");
        mMiv.saveImageToFile(mIv);
    }
}
