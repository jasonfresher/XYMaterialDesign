package com.xy.materialdesign.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by jason on 2017/7/3.
 */

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private int[] attrs = new int[]{android.R.attr.listDivider};
    private Drawable dividerDrawable;

    public GridItemDecoration(Context context) {
        TypedArray ta = context.obtainStyledAttributes(attrs);
        dividerDrawable = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVerticalDivider(c, parent);
        drawHorizontalDivider(c, parent);
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getRight() + params.rightMargin;
            int top = childView.getTop() - params.topMargin;
            int right = left + dividerDrawable.getIntrinsicWidth();
            int bottom = childView.getBottom() + params.bottomMargin;
            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View childView = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) childView.getLayoutParams();
            int left = childView.getLeft() - params.leftMargin;
            int top = childView.getBottom() + params.bottomMargin;
            int right = childView.getRight() + params.rightMargin;
            int bottom = top + dividerDrawable.getIntrinsicHeight();
            dividerDrawable.setBounds(left,top,right,bottom);
            dividerDrawable.draw(c);
        }
    }

    private boolean isLastRow(int position,RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager){
            int spanCount = ((GridLayoutManager)layoutManager).getSpanCount();
            int childCount = layoutManager.getItemCount();
            int lastRowCount = childCount%spanCount;
            //最后一行的数量小于spanCount
            if(lastRowCount == 0 && position >= childCount - spanCount) {
                return true;
            }else if(lastRowCount != 0 && position >= childCount - lastRowCount) {
                return true;
            }
        }
        return false;
    }

    private boolean isLastColum(int position,RecyclerView parent) {
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int spanCount = layoutManager.getSpanCount();
        if((position + 1) % spanCount == 0)
            return true;
        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        // 四个方向的偏移值
        int right = dividerDrawable.getIntrinsicWidth();
        int bottom = dividerDrawable.getIntrinsicHeight();
        if(isLastColum(itemPosition,parent)){//是否是最后一列
            right = 0;
        }
        if(isLastRow(itemPosition,parent)){//是最后一行
            bottom = 0;
        }
        outRect.set(0, 0, right , bottom);
    }
}
