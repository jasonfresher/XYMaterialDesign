package com.xy.materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.xy.materialdesign.R;
import com.xy.materialdesign.animation.XYItemTouchHelperCallBack;
import com.xy.materialdesign.widget.XYRecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by jason on 2017/6/30.
 */

public class XYAdapter extends XYBaseAdapter<String> implements XYItemTouchHelperCallBack.XYMoveListener{


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
    public void onMove(RecyclerView recyclerView, int srcPosition, int targetPosition) {
        int headerViewCount = 0;
        if(recyclerView instanceof XYRecyclerView){
            headerViewCount = ((XYRecyclerView)recyclerView).getHeaderViewCount();
        }
        Collections.swap(mItems,srcPosition - 1 == -1 ? 0 : srcPosition-headerViewCount,targetPosition > getItemCount() ? getItemCount() : targetPosition - headerViewCount);
        notifyItemMoved(srcPosition,targetPosition);
    }
}
