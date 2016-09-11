package com.fichardu.viewshot;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fichardu.viewshot.beans.Person;
import com.fichardu.viewshot.utils.Cheeses;
import com.fichardu.viewshot.utils.Utils;
import com.tplink.halohoop.screenshot.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRcv;
    private ActivityManager mAm;
    private ListView mLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Person> datas = Utils.prepareDatas(MainActivity.this);

        final GlobalScreenshot screenshot = new GlobalScreenshot(this);
        final View viewById1 = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        final View viewById = findViewById(R.id.main_btn);
        final View viewById2 = findViewById(R.id.main_btn2);
        final View viewById3 = findViewById(R.id.main_btn3);
        viewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkScrollable();
            }
        });
        viewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actScroll();
            }
        });
//        mRcv = (RecyclerView) findViewById(R.id.rcv);
//
//        mRcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        mRcv.setAdapter(new MyAdapter(MainActivity.this, datas));


        mLv = (ListView) findViewById(R.id.lv);
        mLv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, Cheeses.CHEESES));

        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //6.0增加了运行时权限，需要动态检测以下是否可用，用户手动允许的时候才可用；
                if (!Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                    return;
                } else {
                    //Android6.0以上
                    Toast.makeText(MainActivity.this, "Halohoop", Toast.LENGTH_SHORT).show();
//                screenshot.takeScreenshot(getWindow().getDecorView(), new Runnable() {
                    screenshot.takeScreenshot(mRcv, new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Halohoop", Toast.LENGTH_SHORT).show();
                        }
                    }, true, true);
                }
            }
        });
    }

    private void actScroll() {
        mAm = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
        Class<? extends ActivityManager> aClass = mAm.getClass();
        Method[] methods = aClass.getMethods();
        Method methodScrollFocusedView = null;
        for (int i = 0; i < methods.length; i++) {
//            Log.i("halohoop", "name:" + methods[i].getName());
            if (methods[i].getName().equals("scrollFocusedView")) {
                methodScrollFocusedView = methods[i];
            }
        }
        methodScrollFocusedView.setAccessible(true);


        Point screenSize = Utils.getScreenSize(MainActivity.this);
        Rect rect = new Rect();
        rect.set(0, screenSize.y / 4, screenSize.x, screenSize.y / 4 * 3);

        try {
            methodScrollFocusedView.invoke(mAm, rect);
        } catch (IllegalAccessException e) {
            Log.i("halohoop", "方法执行出错");
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void checkScrollable() {
        mAm = (ActivityManager) MainActivity.this.getSystemService(Context.ACTIVITY_SERVICE);
        Class<? extends ActivityManager> aClass = mAm.getClass();
        Method[] methods = aClass.getMethods();
        Method methodScrollFocusedView = null;
        Method methodCanFocusedViewScrollVertically = null;
        for (int i = 0; i < methods.length; i++) {
//            Log.i("halohoop", "name:" + methods[i].getName());
            if (methods[i].getName().equals("scrollFocusedView")) {
                methodScrollFocusedView = methods[i];
            }
            if (methods[i].getName().equals("canFocusedViewScrollVertically")) {
                methodCanFocusedViewScrollVertically = methods[i];
            }
        }
        Class<?>[] parameterTypes = methodScrollFocusedView.getParameterTypes();
//        for (int i = 0; i < parameterTypes.length; i++) {
//            Log.i("halohoop", "param:" + parameterTypes[i]);
//        }
        Class<?>[] parameterTypes2 = methodCanFocusedViewScrollVertically.getParameterTypes();
//        for (int i = 0; i < parameterTypes2.length; i++) {
//            Log.i("halohoop", "param2:" + parameterTypes2[i]);
//        }
        methodScrollFocusedView.setAccessible(true);
        methodCanFocusedViewScrollVertically.setAccessible(true);


        Point screenSize = Utils.getScreenSize(MainActivity.this);
        Rect rect = new Rect();
        rect.set(0, screenSize.y / 4, screenSize.x, screenSize.y / 4 * 3);

//        try {
//            methodScrollFocusedView.invoke(mAm, rect);
//        } catch (IllegalAccessException e) {
//            Log.i("halohoop", "方法执行出错");
//            e.printStackTrace();
//
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }

        try {
            Object invoke = methodCanFocusedViewScrollVertically.invoke(mAm, rect);
            Log.i("halohoop", "方法执行结果：" + invoke);
        } catch (IllegalAccessException e) {
            Log.i("halohoop", "方法执行出错");
            e.printStackTrace();

        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
