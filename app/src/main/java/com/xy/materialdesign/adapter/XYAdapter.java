package com.xy.materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.xy.materialdesign.R;
import com.xy.materialdesign.listeners.XYMoveListener;
import com.xy.materialdesign.ui.XYItemTouchHelperCallBack;
import com.xy.materialdesign.widget.XYRecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by jason on 2017/6/30.
 */

public class XYAdapter extends XYBaseAdapter<String> implements XYMoveListener {


    public XYAdapter(List<String> items) {
        super(items);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.recyclerview_item;
    }

    @Override
    protected void bindData(XYViewHolder holder, int position) {
        TextView tv = (TextView) holder.findView(R.id.content);
        tv.setText(mItems.get(position) + "");
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, int srcPosition, int targetPosition) {
        Collections.swap(mItems, srcPosition, targetPosition);
        notifyItemMoved(srcPosition, targetPosition);
        return true;
    }

    @Override
    public boolean onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        mItems.remove(position);
        notifyItemRemoved(position);
        return true;
    }
}
