package com.xy.materialdesign;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jason on 2017/6/30.
 */

public abstract class XYBaseAdapter<T> extends RecyclerView.Adapter<XYBaseAdapter<T>.XYViewHolder> {

    protected List<T> mItems;

    private OnItemClickListener mOnItemClickListener;

    private boolean mIsStaggerLayout = false;

    public XYBaseAdapter(List<T> items) {
        mItems = items;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public XYViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(getContentLayoutId(), parent, false);
        XYViewHolder viewHolder = new XYViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(XYViewHolder holder, int position) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if(mIsStaggerLayout){
            lp.height = (300 + (position % 3) * 30);
        }else{
            lp.height = 100;
        }
        holder.itemView.setOnClickListener(new XYOnItemClickListener(holder.getLayoutPosition()));
        bindData(holder, position);
    }

    protected abstract int getContentLayoutId();

    protected abstract void bindData(XYViewHolder holder, int position);


    @Override
    public int getItemCount() {
        return mItems.size();
    }


    /**
     * 刷新数据
     * @param datas
     */
    public void refresh(List<T> datas){
        this.mItems.clear();
        this.mItems.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param datas
     */
    public void addData(List<T> datas){
        this.mItems.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(T t,int position){
        this.mItems.add(position,t);
        notifyItemInserted(position);
    }

    public void removeData(int position){
        this.mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void removeData(){
        this.mItems.clear();
        notifyDataSetChanged();
    }

    class XYViewHolder extends RecyclerView.ViewHolder {

        private Map<Integer, View> mCache = new HashMap<>();

        public XYViewHolder(View itemView) {
            super(itemView);
        }

        public View findView(int res) {
            if (mCache.get(res) == null) {
                mCache.put(res, itemView.findViewById(res));
            }
            return mCache.get(res);
        }
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position);
    }


    public class XYOnItemClickListener implements View.OnClickListener {

        int mPosition;

        public XYOnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.OnItemClick(v, mPosition);
        }
    }

    public void setStaggerLayout(boolean isStaggerLayout){
        mIsStaggerLayout =  isStaggerLayout;
    }
}
