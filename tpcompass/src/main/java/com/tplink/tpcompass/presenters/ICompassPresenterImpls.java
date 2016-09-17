package com.tplink.tpcompass.presenters;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraDevice;
import android.util.Size;

import com.tplink.tpcompass.views.ICompassActivity;

/**
 * Created by Pooholah on 2016/9/17.
 */
public class ICompassPresenterImpls extends CameraDevice.StateCallback
        implements com.tplink.tpcompass.presenters.ICompassPresenter {
    private ICompassActivity mICompassActivity;
    private Size mPreviewSize;

    public ICompassPresenterImpls(ICompassActivity iCompassActivity) {
        this.mICompassActivity = iCompassActivity;
    }

    @Override
    public void onOpened(CameraDevice camera) {

    }

    @Override
    public void onDisconnected(CameraDevice camera) {

    }

    @Override
    public void onError(CameraDevice camera, int error) {

    }

    //[TextureView]
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
//        try {

//            //获得CameraManager
//            CameraManager cameraManager =
//                    (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);
//            //获得属性
//            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(0);
//            //支持的STREAM CONFIGURATION
//            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
//            //显示的size
//            mPreviewSize = map.getOutputSizes(SurfaceTexture.class)[0];
//            //打开相机
//            if (ActivityCompat.checkSelfPermission(getContext(),
//                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            cameraManager.openCamera(0, this, null);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public Context getContext() {
        return mICompassActivity.getContext();
    }
    //[TextureView]
}
