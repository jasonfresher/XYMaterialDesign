package com.xy.materialdesign.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xy.materialdesign.R;
import com.xy.materialdesign.adapter.XYAdapter;
import com.xy.materialdesign.adapter.XYBaseAdapter;
import com.xy.materialdesign.divider.DividerItemDecoration;
import com.xy.materialdesign.divider.GridItemDecoration;
import com.xy.materialdesign.widget.XYRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jason on 2017/6/30.
 */

public class RecyclerViewActivity extends AppCompatActivity{

    private Handler handler = new Handler();
    private XYRecyclerView rv;
    private List<String> items;
    private XYAdapter xyAdapter;
    private RecyclerView.ItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        rv = (XYRecyclerView) findViewById(R.id.recyclerview);
        xyAdapter = new XYAdapter(items);
        xyAdapter.setOnItemClickListener(new XYBaseAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击" + position, Toast.LENGTH_SHORT).show();
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

        /**
         * 添加条目间隔线
         */
        dividerItemDecoration =  new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
        rv.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new XYItemTouchHelperCallBack(xyAdapter));
        itemTouchHelper.attachToRecyclerView(rv);
    }

    /**
     * 添加一个item
     * @param view
     */
    public void addItem(View view){
        xyAdapter.addData("add item",3);
    }

    /**
     * 删除一个item
     * @param view
     */
    public void removeItem(View view){
        xyAdapter.removeData(3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recycler_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        rv.removeItemDecoration(dividerItemDecoration);
        switch (item.getItemId()) {
            case R.id.action_LinearLayoutManager_HORIZATOAL:
                xyAdapter.setStaggerLayout(false);
                dividerItemDecoration =  new DividerItemDecoration(this,LinearLayoutManager.HORIZONTAL);
                rv.addItemDecoration(dividerItemDecoration);
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                break;
            case R.id.action_LinearLayoutManager_VERTICAL:
                xyAdapter.setStaggerLayout(false);
                dividerItemDecoration =  new DividerItemDecoration(this,LinearLayoutManager.VERTICAL);
                rv.addItemDecoration(dividerItemDecoration);
                rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                break;
            case R.id.action_GridLayoutManager:
                xyAdapter.setStaggerLayout(false);
                dividerItemDecoration =  new GridItemDecoration(this);
                rv.addItemDecoration(dividerItemDecoration);
                rv.setLayoutManager(new GridLayoutManager(this, 3));
                break;
            case R.id.action_StaagerLayoutManager:
                xyAdapter.setStaggerLayout(true);
                rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
