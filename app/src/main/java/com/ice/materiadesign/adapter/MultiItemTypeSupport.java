package com.ice.materiadesign.adapter;

/**
 * Created by asd on 1/13/2017.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);
}
