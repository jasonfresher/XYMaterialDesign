package com.xy.materialdesign.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by jason on 2017/7/4.
 */

public class HeaderViewAdapter extends RecyclerView.Adapter {

    private ArrayList<View> mHeaderViewInfos;
    private ArrayList<View> mfooterViewInfos;
    private RecyclerView.Adapter mAdapter;

    public HeaderViewAdapter(ArrayList<View> headerViewInfos, ArrayList<View> footerViewInfos, RecyclerView.Adapter adapter) {
        this.mHeaderViewInfos = headerViewInfos;
        this.mfooterViewInfos = footerViewInfos;
        mAdapter = adapter;
    }

    public RecyclerView.Adapter getInnerAdapter(){
        return mAdapter;
    }

    @Override
    public int getItemViewType(int position) {
        int numHeaders = mHeaderViewInfos.size();
        if(position < numHeaders)
            return RecyclerView.INVALID_TYPE;
        if (mAdapter != null && position >= numHeaders) {
            int adjPosition = position - numHeaders;
            int adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return RecyclerView.INVALID_TYPE - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == RecyclerView.INVALID_TYPE){
            return new HeaderViewHolder(mHeaderViewInfos.get(0));
        }else if(viewType == RecyclerView.INVALID_TYPE - 1){
            return new HeaderViewHolder(mfooterViewInfos.get(0));
        }else{
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int numHeaders = mHeaderViewInfos.size();
        if (position < numHeaders) {
            return;
        }
        int adjPosition = position - numHeaders;
        int adapterCount = mAdapter.getItemCount();
        if (mAdapter != null && adjPosition < adapterCount) {
            mAdapter.onBindViewHolder(holder, adjPosition);
        }
    }

    @Override
    public int getItemCount() {
        if (mAdapter != null) {
            return mfooterViewInfos.size() + mHeaderViewInfos.size() + mAdapter.getItemCount();
        } else {
            return mfooterViewInfos.size() + mHeaderViewInfos.size();
        }
    }

    public int getHeaderViewsCount() {
        return mHeaderViewInfos.size();
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder{
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
