package com.fichardu.viewshot.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.fichardu.viewshot.beans.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pooholah on 2016/9/11.
 */

public class Utils {
    public static List<Person> prepareDatas(Context context) {
        List<Person> arrayList = new ArrayList<>();
        for (int i = 0; i < 132; i++) {
            Person person = new Person();
            person.setName(Cheeses.CHEESES[i]);
            arrayList.add(person);
        }
        return arrayList;
    }
    @SuppressLint("NewApi")//getSize(方法)需要 api13才能使用
/**
 * 通过返回值point拿宽高
 * point.x 屏幕的宽
 * point.y 屏幕的高
 */
    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point out = new Point();
        //Build.VERSION_CODES.HONEYCOMB_MR2 → 13
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(out);
        } else {
            int width = display.getWidth();
            int height = display.getHeight();
            out.set(width, height);
        }
        return out;
    }
}
