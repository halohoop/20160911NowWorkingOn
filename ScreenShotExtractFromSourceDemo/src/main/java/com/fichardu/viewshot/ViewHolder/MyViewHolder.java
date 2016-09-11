package com.fichardu.viewshot.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tplink.halohoop.screenshot.R;

/**
 * Created by Pooholah on 2016/9/11.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public MyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.tv);
    }
}
