package com.halohoop.mergebitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.halohoop.mergebitmap.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.abc1);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.abc2);

        Bitmap bitmap = Utils.addVBitmap(bitmap1, bitmap2);
        bitmap1.recycle();
        bitmap2.recycle();

        iv.setImageBitmap(bitmap);
    }
}
