package com.djxiao.cityselecteddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvTicket;
    private RecyclerView recyclerView;
    private ListView list;
    private CityAdapter adapter;
    private FirstPinYinAdapter pinyinAdapter;
    private RecyclerView.LayoutManager manager;
    private pinyinComparator comparator;

    private List<CityBean> cityBeans = new ArrayList<CityBean>();
    private ArrayList<String> firstPinYin = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){

        tvTicket = (TextView) findViewById(R.id.tv_ticket);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        comparator = new pinyinComparator();
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new CityAdapter(this);
        recyclerView.setAdapter(adapter);

        for(int i = 0;i<Cities.stringCitys.length;i++){
            cityBeans.add(new CityBean(Cities.stringCitys[i],
                    transferToPinYin(Cities.stringCitys[i]),
                    transferToPinYin(Cities.stringCitys[i]).substring(0,1)));
        }

        Collections.sort(cityBeans,comparator);
        for(CityBean cityBean : cityBeans){
            if(!firstPinYin.contains(cityBean.cityFirstPinyin)){
                firstPinYin.add(cityBean.cityFirstPinyin);
            }
        }

        adapter.addItems(cityBeans);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View ticketView = recyclerView.findChildViewUnder(tvTicket.getMeasuredWidth(),5);
                if(ticketView != null && ticketView.getContentDescription() != null){
                    tvTicket.setText(String.valueOf(ticketView.getContentDescription()));
                }

                View targetView = recyclerView.findChildViewUnder(tvTicket.getMeasuredWidth(),tvTicket.getMeasuredHeight()+1);
                if(targetView != null && targetView.getTag() != null){
                    int statue = (int) targetView.getTag();
                    float y = targetView.getTop() - tvTicket.getMeasuredHeight();
                    if(statue == adapter.HAS_STICKY_VIEW){
                        if(targetView.getTop() > 0){
                            tvTicket.setTranslationY(y);
                        }else{
                            tvTicket.setTranslationY(0);
                        }
                    }else if(statue == adapter.NONE_STICKY_VIEW){
                        tvTicket.setTranslationY(0);
                    }
                }
            }
        });

        list = (ListView) findViewById(R.id.list);
        list.setDivider(null);
        pinyinAdapter = new FirstPinYinAdapter(this);
        list.setAdapter(pinyinAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                int selectPosition = 0;
                for(int i = 0;i < cityBeans.size();i++){
                    if(pinyinAdapter.getItem(position).equals(cityBeans.get(i).cityFirstPinyin)){
                        selectPosition = i;
                        break;
                    }
                }
                recyclerView.scrollToPosition(selectPosition);
            }
        });
        pinyinAdapter.addItems(firstPinYin);

    }

    private String transferToPinYin(String str){
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<str.length();i++){
            sb.append(Pinyin.toPinyin(str.charAt(i)));
        }
        return sb.toString();
    }


    public class pinyinComparator implements Comparator<CityBean>{
        @Override
        public int compare(CityBean cityBean, CityBean t1) {
            return cityBean.cityFirstPinyin.compareTo(t1.cityFirstPinyin);
        }
    }


}
