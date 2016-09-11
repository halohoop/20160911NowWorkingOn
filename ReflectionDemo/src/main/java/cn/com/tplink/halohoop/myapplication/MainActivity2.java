package cn.com.tplink.halohoop.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.Method;

public class MainActivity2 extends AppCompatActivity {

    private final String TAG = "halohoop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Class<?> aClass = Class.forName("android.app.ActivityManager");
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                Log.i(TAG, declaredMethod.getName());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
