/*
 * Copyright (C) 2011, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * CropLayout.java
 *
 * Description
 *
 * Author heyuming
 *
 * Ver 1.0, Jan 14, 2016, heyuming, Create file
 */

package cn.com.tplink.utils.crop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import cn.com.tplink.halohoop.myapplication.R;


public final class CropLayout extends FrameLayout implements OnClickListener {

    private ImageButton mConfirmButton;

    private ImageButton mCancelButton;

    private BroadcastReceiver mHomeKeyEventReceiver;

    private CropView mCropView;

    private Context mContext;

    /* Default callback do nothing */
    private Callback mCallback = new Callback() {

        @Override
        public void confirm() {
        }

        @Override
        public void cancel() {
        }

        @Override
        public void back() {
        }

        public void home() {
        }
    };

    public CropLayout(Context context) {
        super(context);
        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater)context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.global_crop_screenshot, this);
        mConfirmButton = (ImageButton)findViewById(R.id.btn_confirm);
        mConfirmButton.setOnClickListener(this);
        mCancelButton = (ImageButton)findViewById(R.id.btn_cancel);
        mCancelButton.setOnClickListener(this);
        mCropView = (CropView)findViewById(R.id.crop_view);

        mHomeKeyEventReceiver = new HomeKeyEventReceiver();
    }

    @Override
    protected void onAttachedToWindow() {
        mContext.registerReceiver(mHomeKeyEventReceiver, new IntentFilter(
                Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        mContext.unregisterReceiver(mHomeKeyEventReceiver);
        super.onDetachedFromWindow();
    }

    public CropView getCropView() {
        return mCropView;
    }

    public ImageButton getConfirmButton() {
        return mConfirmButton;
    }

    public ImageButton getCancelButton() {
        return mCancelButton;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mCallback.back();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                mCallback.confirm();
                break;
            case R.id.btn_cancel:
                mCallback.cancel();
                break;
            default:
                break;
        }
    }

    public void setCallback(Callback b) {
        mCallback = b;
    }

    public interface Callback {

        /**
         * Called when the confirm button is clicked.
         */
        public void confirm();

        /**
         * Called when the cancel button is clicked.
         */
        public void cancel();

        /**
         * Called when the Back key is clicked.
         */
        public void back();

        /**
         * Called when the Home & Recent key is clicked.
         */
        public void home();

    }

    /**
     * Receiver for listening home & recent key
     */
    class HomeKeyEventReceiver extends BroadcastReceiver {

        String SYSTEM_REASON = "reason";

        String SYSTEM_HOME_KEY = "homekey";

        String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_REASON);
                if (TextUtils.equals(reason, SYSTEM_HOME_KEY)
                        || TextUtils.equals(reason, SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    mCallback.home();
                }
            }
        }

    }

}
