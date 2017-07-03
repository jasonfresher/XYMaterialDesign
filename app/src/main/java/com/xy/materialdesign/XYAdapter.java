package com.xy.materialdesign;

import android.widget.TextView;

import java.util.List;

/**
 * Created by jason on 2017/6/30.
 */

public class XYAdapter extends XYBaseAdapter<String> {


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
}
