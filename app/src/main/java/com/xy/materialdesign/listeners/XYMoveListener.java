package com.xy.materialdesign.listeners;

import android.support.v7.widget.RecyclerView;

/**
 * Created by jason on 2017/7/5.
 */

public interface XYMoveListener {
    boolean onMove(RecyclerView recyclerView, int srcPosition, int targetPosition);
    boolean onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
}
