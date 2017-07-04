package com.xy.materialdesign.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.xy.materialdesign.adapter.HeaderViewAdapter;

import java.util.ArrayList;

/**
 * Created by jason on 2017/7/4.
 */

public class XYRecyclerView extends RecyclerView {

    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private HeaderViewAdapter mWrapAdapter;
    ArrayList<View> mHeaderViewInfos = new ArrayList();
    ArrayList<View> mFooterViewInfos = new ArrayList();
    private boolean isRegisterDataObserver;

    public XYRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void addHeaderView(View v){
        mHeaderViewInfos.add(v);
    }


    public void addFooterView(View v){
        mFooterViewInfos.add(v);
    }


    @Override
    public void setAdapter(Adapter adapter) {
        /**
         * 如果mWrapAdapter不为null
         */
        if (mWrapAdapter != null && mDataObserver != null && isRegisterDataObserver) {
            mWrapAdapter.getInnerAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        if ((mHeaderViewInfos.size() > 0|| mFooterViewInfos.size() > 0) && adapter != null) {
            mWrapAdapter = new HeaderViewAdapter(mHeaderViewInfos, mFooterViewInfos, adapter);
            super.setAdapter(mWrapAdapter);
            mWrapAdapter.getInnerAdapter().registerAdapterDataObserver(mDataObserver);
            mDataObserver.onChanged();
            isRegisterDataObserver = true;
        } else {
            super.setAdapter(adapter);
        }
    }

    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
             mWrapAdapter.notifyItemRangeChanged(positionStart + mWrapAdapter.getHeaderViewsCount() + 1, itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart + mWrapAdapter.getHeaderViewsCount() + 1, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart + mWrapAdapter.getHeaderViewsCount() + 1, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            int headerViewsCountCount = mWrapAdapter.getHeaderViewsCount();
            mWrapAdapter.notifyItemRangeChanged(fromPosition + headerViewsCountCount + 1, toPosition + headerViewsCountCount + 1 + itemCount);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mWrapAdapter != null && mDataObserver != null && isRegisterDataObserver) {
            mWrapAdapter.getInnerAdapter().unregisterAdapterDataObserver(mDataObserver);
            isRegisterDataObserver = false;
        }

    }

}
