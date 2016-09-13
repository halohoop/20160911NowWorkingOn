package com.halohoop.scrollimageviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.halohoop.scrollimageviewdemo.views.ScrollImageView;

public class MainActivity extends AppCompatActivity {

    private ScrollImageView siv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        siv = (ScrollImageView) findViewById(R.id.siv);
        ViewTreeObserver viewTreeObserver = siv.getViewTreeObserver();
        viewTreeObserver.addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                siv.reset();
            }
        });
    }

    public void reset(View v) {
        siv.reset();
    }
}
