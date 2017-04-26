package com.ice.materiadesign;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.ice.materiadesign.adapter.NormalAdapter;

import java.util.Collections;

/**
 * Created by asd on 4/9/2017.
 */

public class DragItem extends ItemTouchHelper.Callback {

    private NormalAdapter adapter;

    public DragItem(NormalAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * 返回可以拖动的方向与滑动删除的方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int dragFlags;
        int swipeFlags;
        if(layoutManager instanceof LinearLayoutManager){
            dragFlags = ItemTouchHelper.UP |
                    ItemTouchHelper.DOWN;
            swipeFlags = ItemTouchHelper.LEFT;
        }else{
            dragFlags = ItemTouchHelper.UP |
                    ItemTouchHelper.DOWN |
                    ItemTouchHelper.RIGHT |
                    ItemTouchHelper.LEFT;
            swipeFlags = 0;//不能被滑动删除
        }
        return makeMovementFlags(dragFlags,swipeFlags);

    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if(fromPosition<toPosition){
            for(int i = fromPosition;i<toPosition;i++){
                Collections.swap(adapter.getData(), i ,i + 1);
            }
        }else{
            for(int i = fromPosition;i>toPosition;i--){
                Collections.swap(adapter.getData(),i, i -1);
            }
        }
        recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
//        if(direction == ItemTouchHelper.END){
//            adapter.getData().remove(position);
//            adapter.notifyItemRemoved(position);
//        }
        adapter.onItemDissmiss(position);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    /**
     * 是否支持侧滑删除
     * 默认返回true
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
