package com.xy.materialdesign.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/7/1 0001.
 */

public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attrs = new int[]{android.R.attr.listDivider};
    private Drawable dividerDrawable;
    private int mOrientation;

    public DividerItemDecoration(Context context, int orientation) {
        TypedArray ta = context.obtainStyledAttributes(attrs);
        dividerDrawable = ta.getDrawable(0);
        ta.recycle();
        setOrientation(orientation);
    }

    private void setOrientation(int orientation) {
        if (orientation != LinearLayoutManager.HORIZONTAL && orientation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("布局参数错误");
        }
        this.mOrientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawVerticalDivider(c, parent);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c, parent);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int bottom = childView.getBottom() + params.bottomMargin;
            int left = childView.getRight() + params.rightMargin;
            int right = left + dividerDrawable.getIntrinsicWidth();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        int left = parent.getLeft() + parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int top = childView.getBottom() + params.topMargin + Math.round(ViewCompat.getTranslationY(childView));
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left, top, right, bottom);
            dividerDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            outRect.set(0, 0, dividerDrawable.getIntrinsicWidth(), 0);
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, dividerDrawable.getIntrinsicHeight());
        }
    }
}
