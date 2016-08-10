package com.djxiao.cityselecteddemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djxiao
 * @create 2016/8/10 13:42
 * @DESC
 */
public class CityAdapter extends RecyclerView.Adapter{

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    public List<CityBean> dataArr = new ArrayList<CityBean>();
    public LayoutInflater inflater;

    public CityAdapter(Context context){
        inflater = LayoutInflater.from(context);
//        this.dataArr = dataArr;
    }

    public void addItems(List<CityBean> arr){
        dataArr.addAll(arr);
        this.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_city,null);
        ViewHolder holder = new ViewHolder(view);
        holder.tvCityName = (TextView) view.findViewById(R.id.tv_city_name);
        holder.tvTicket = (TextView) view.findViewById(R.id.tv_ticket);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            ViewHolder cityHolder = (ViewHolder) holder;

            CityBean bean = dataArr.get(position);

            cityHolder.tvCityName.setText(bean.cityName);
            cityHolder.tvTicket.setText(bean.cityFirstPinyin);

            if(position == 0){
                cityHolder.itemView.setTag(FIRST_STICKY_VIEW);
                cityHolder.tvTicket.setVisibility(View.VISIBLE);
            }else{
                if(!bean.cityFirstPinyin.equals(dataArr.get(position-1).cityFirstPinyin)){
                    cityHolder.itemView.setTag(HAS_STICKY_VIEW);
                    cityHolder.tvTicket.setVisibility(View.VISIBLE);
                }else{
                    cityHolder.itemView.setTag(NONE_STICKY_VIEW);
                    cityHolder.tvTicket.setVisibility(View.GONE);
                }
            }

            cityHolder.itemView.setContentDescription(bean.cityFirstPinyin);
        }
    }

    @Override
    public int getItemCount() {
        return dataArr.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        private TextView tvCityName;
        private TextView tvTicket;

    }

}
