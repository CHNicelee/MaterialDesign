package com.ice.materiadesign;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asd on 1/13/2017.
 */

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    //分隔线
    Drawable mDivider;

    Context mContext;

    //list方向
    int mOrientation;


    //我们通过获取系统属性中的listDivider来添加，在系统中的AppTheme中设置
    public static final int[] ATRRS  = new int[]{
            android.R.attr.listDivider
    };

    public MyItemDecoration(Context context,int orientation){
        mContext = context;
        mOrientation = orientation;
        TypedArray ta = mContext.obtainStyledAttributes(ATRRS);
        mDivider = ta.getDrawable(0);
        ta.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation== LinearLayoutManager.HORIZONTAL){
            drawHorizontalDivider(c,parent,state);
        }else {
            drawVerticalDivider(c,parent,state);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            //分隔线的top和bottom
            int top = view.getBottom()+params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            //分隔线的left和right
            int left =view.getRight()+ params.rightMargin;
            int right= left + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bottom);
            mDivider.draw(c);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        if(mOrientation== LinearLayoutManager.VERTICAL){
                outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else {
            outRect.set(0,0,mDivider.getIntrinsicHeight(),0);
        }
    }

}
