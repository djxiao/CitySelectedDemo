package com.djxiao.cityselecteddemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author djxiao
 * @create 2016/8/10 16:24
 * @DESC
 */
public class FirstPinYinAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<String> dataArr = new ArrayList<String>();

    public FirstPinYinAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }

    public void addItems(ArrayList<String> arr){
        dataArr.addAll(arr);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataArr.size();
    }

    @Override
    public Object getItem(int i) {
        return dataArr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_first_pinyin,null);
            holder.tvFirstPinYin = (TextView) view.findViewById(R.id.tv_first_pin_yin);
        }

        String pinyin = dataArr.get(i);
        holder.tvFirstPinYin.setText(pinyin);

        return view;
    }

    public class ViewHolder{
        private TextView tvFirstPinYin;
    }
}
