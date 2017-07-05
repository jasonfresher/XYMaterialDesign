package com.xy.materialdesign.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.materialdesign.R;
import com.xy.materialdesign.adapter.XYAdapter;
import com.xy.materialdesign.adapter.XYBaseAdapter;
import com.xy.materialdesign.widget.XYRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/7/5.
 */

public class HeaderRecyclerViewActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private XYRecyclerView rv;
    private List<String> items;
    private XYAdapter xyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header_recyclerview);
        items = new ArrayList<>();
        for (int i = 0; i < 90; i++) {
            items.add("条目" + i);
        }

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.background));
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        rv = (XYRecyclerView) findViewById(R.id.header_recyclerview);
        xyAdapter = new XYAdapter(items);
        xyAdapter.setOnItemClickListener(new XYBaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Toast.makeText(HeaderRecyclerViewActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        /**
         * 添加头部view
         */
        TextView tv = new TextView(this);
        tv.setTextSize(20);
        tv.setGravity(Gravity.CENTER);
        tv.setText("this is a title");
        RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(params);
        rv.addHeaderView(tv);

        /**
         * 添加尾部view
         */
        TextView tv2 = new TextView(this);
        tv2.setTextSize(20);
        tv2.setGravity(Gravity.CENTER);
        tv2.setText("this is a footer");
        RecyclerView.LayoutParams params2 = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,RecyclerView.LayoutParams.WRAP_CONTENT);
        tv2.setLayoutParams(params2);
        rv.addFooterView(tv2);

        rv.setAdapter(xyAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }
}
