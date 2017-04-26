package com.ice.materiadesign.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by asd on 1/13/2017.
 */

public abstract class MultiItemCommonAdapter<T> extends CommentAdapter<T> {

    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;


    public MultiItemCommonAdapter(Context context, List<T> datas,MultiItemTypeSupport<T> multiItemTypeSupport) {

        super(context, -1, datas);
        mMultiItemTypeSupport  = multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return mMultiItemTypeSupport.getItemViewType(position,mDatas.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder = ViewHolder.get(mContext,parent,layoutId);
        return holder;
    }
}
