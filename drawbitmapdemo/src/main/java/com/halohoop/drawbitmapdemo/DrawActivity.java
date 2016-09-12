/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * DrawActivity.java
 *
 * Description
 *
 * Author huanghaiqi
 *
 * Ver 1.0, 2016-09-12, huanghaiqi, Create file
 */

package com.halohoop.drawbitmapdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.OutputStream;

public class DrawActivity extends Activity implements OnTouchListener, OnClickListener {

    Button btn;
    ImageView imageView;
    Button save;
    Button clear;
    Bitmap bmp;
    Bitmap drawBitmap;
    Canvas canvas;
    Paint paint;

    float downX = 0;
    float downY = 0;
    float upX = 0;
    float upY = 0;

    private static final int CHOOSE_PIC = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        btn = (Button) findViewById(R.id.btn1);
        imageView = (ImageView) findViewById(R.id.imageview);
        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);
        btn.setOnClickListener(this);
        imageView.setOnTouchListener(this);
        save.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images
                    .Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, CHOOSE_PIC);
        } else if (view == save) {
            if (drawBitmap != null) {
                try {
                    Uri imageUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, new
                            ContentValues());
                    OutputStream os = getContentResolver().openOutputStream(imageUri);
                    /**
                     *  compress方法将图片转换成JPG或者PNG格式
                     *PNG非常适合艺术线条和图形，JPG适合带渐变的全彩图像，如照片
                     *第二个参数为质量设置，只有当格式为jpg时该参数才会生效，PNG将始终保持所有的数据，从而使质量设置无效
                     *质量设置越大，图片越大，效果越好。
                     *
                     */
                    drawBitmap.compress(CompressFormat.JPEG, 90, os);
                    Toast.makeText(this, "Saved：" + imageUri.toString(), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else if (view == clear) {
            paint = new Paint();
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            canvas.drawPaint(paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC));
            canvas.drawBitmap(bmp, createMatrix(), paint);
        }

    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_CANCEL:

                break;
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                imageView.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                upX = event.getX();
                upY = event.getY();
                canvas.drawLine(downX, downY, upX, upY, paint);
                canvas.drawCircle(100, 100, 50, paint);
                imageView.invalidate();
                downX = upX;
                downY = upY;
                break;
            default:
                break;
        }
        return true;
    }

    private Canvas createCanvas(Bitmap bitmap) {
        Canvas tmpCanvas = new Canvas(bitmap);
        return tmpCanvas;
    }

    private Paint creatPaint() {
        Paint tmpPaint = new Paint();
        tmpPaint.setColor(Color.GREEN);
        tmpPaint.setStrokeWidth(10);
        return tmpPaint;
    }

    private Matrix createMatrix() {
        Matrix matrix = new Matrix();
        return matrix;
    }

    private Bitmap loadBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int dw = metrics.widthPixels;
            int dh = metrics.heightPixels;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null,
                    options);
            int heightRadio = (int) Math.ceil(options.outHeight / dh);
            int widthRadio = (int) Math.ceil(options.outWidth / dw);
            if (heightRadio > 1 && widthRadio > 1) {
                if (heightRadio > widthRadio) {
                    options.inSampleSize = heightRadio;
                } else {
                    options.inSampleSize = widthRadio;
                }
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null,
                    options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PIC:
                    Uri uri = data.getData();
                    bmp = loadBitmap(uri);
                    drawBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp
                            .getConfig());
                    canvas = createCanvas(drawBitmap);
                    paint = creatPaint();
                    canvas.drawBitmap(bmp, createMatrix(), paint);
                    imageView.setImageBitmap(drawBitmap);
                    imageView.setOnTouchListener(this);
                    break;

                default:
                    break;
            }
        }
    }


}