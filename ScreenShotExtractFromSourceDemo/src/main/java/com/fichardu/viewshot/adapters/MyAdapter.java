package com.fichardu.viewshot.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fichardu.viewshot.ViewHolder.MyViewHolder;
import com.fichardu.viewshot.beans.Person;
import com.tplink.halohoop.screenshot.R;

import java.util.List;

/**
 * Created by Pooholah on 2016/9/11.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context mContext;
    private List<Person> datas;

    public MyAdapter(Context context, List<Person> datas) {
        this.datas = datas;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(datas.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}
