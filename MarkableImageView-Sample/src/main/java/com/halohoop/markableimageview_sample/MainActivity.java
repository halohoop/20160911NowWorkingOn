package com.halohoop.markableimageview_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.halohoop.markableimageview.views.MarkableImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "halohoop";
    private MarkableImageView mIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mIv = (MarkableImageView) findViewById(R.id.miv);
    }

    public void enterEditMode(View v) {
        Log.i(TAG, "enter");
        mIv.enterEditMode();
    }

    public void exitEditMode(View v) {
        Log.i(TAG, "exit");
        mIv.exitEditMode();
    }
}
