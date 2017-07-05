package com.xy.materialdesign.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.xy.materialdesign.listeners.XYMoveListener;

/**
 * Created by jason on 2017/7/4.
 */

public class XYItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private XYMoveListener mXyMoveListener;

    public XYItemTouchHelperCallBack(XYMoveListener listener) {
        this.mXyMoveListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int flag = makeMovementFlags(dragFlags, swipeFlags);
        return flag;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder src, RecyclerView.ViewHolder target) {
        int srcPosition = src.getAdapterPosition();
        int targetPosition = target.getAdapterPosition();
        if (mXyMoveListener != null)
           return mXyMoveListener.onMove(recyclerView, srcPosition, targetPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mXyMoveListener != null)
            mXyMoveListener.onSwiped(viewHolder, direction);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

}
