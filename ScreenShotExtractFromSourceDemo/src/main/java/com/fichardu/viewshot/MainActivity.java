package com.fichardu.viewshot;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.tplink.halohoop.screenshot.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GlobalScreenshot screenshot = new GlobalScreenshot(this);
        final View viewById1 = getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        final View viewById = findViewById(R.id.main_btn);
//        final View viewById2 = findViewById(R.id.tv);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //6.0增加了运行时权限，需要动态检测以下是否可用，用户手动允许的时候才可用；
                if(!Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                    startActivity(intent);
                    return;
                } else {
                    //Android6.0以上
                    Toast.makeText(MainActivity.this, "Halohoop", Toast.LENGTH_SHORT).show();
//                screenshot.takeScreenshot(getWindow().getDecorView(), new Runnable() {
                    screenshot.takeScreenshot(viewById1, new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "Halohoop", Toast.LENGTH_SHORT).show();
                        }
                    }, true, true);
                }
            }
        });
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
