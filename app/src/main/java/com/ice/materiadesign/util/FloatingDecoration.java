package com.ice.materiadesign.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by asd on 1/18/2017.
 */

public class FloatingDecoration extends RecyclerView.ItemDecoration{

    private Context mContext;
    private int mTitleHeight;//分隔栏高度
    private Paint mPaint;//画背景的画笔
    private Paint mTextPaint;//画文字的画笔
    private Rect mBounds;//文字边界
    private int mTextSize;
    private int mTextColor;
    private int mBackgroundColor;
    //用于得到type和type对应的Text
    private DecorationListener mListener;


    public FloatingDecoration(Context context,DecorationListener listener){
        mContext = context;
        mTitleHeight = 100;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GRAY);
        mBounds = new Rect();
        mListener = listener;

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(sp2px(context,30));

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
            if (position == 0) {//等于0肯定要有title的
                outRect.set(0, mTitleHeight, 0, 0);
            } else {//其他的通过判断
                if (mListener.getType(position)!= mListener.getType(position-1)) {
                    outRect.set(0, mTitleHeight, 0, 0);//不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                } else {
                    outRect.set(0, 0, 0, 0);
                }
            }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            int position = params.getViewLayoutPosition();
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position);

                } else {//其他的通过判断
                    if (mListener.getType(position)!= mListener.getType(position-1)) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position);
                    }
                }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     */
    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {//最先调用，绘制在最下层
        c.drawRect(left, child.getTop() - params.topMargin - mTitleHeight, right, child.getTop() - params.topMargin, mPaint);
        //绘制文字
        mTextPaint.getTextBounds(mListener.getTypeText(position), 0,mListener.getTypeText(position).length(), mBounds);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        //计算方法： 矩形区域的top + bottom - - fontMetrics.bottom - fontMetrics.top 除以 2
        int baseline = ( child.getTop() - mTitleHeight + child.getTop() - fontMetrics.bottom - fontMetrics.top) / 2;
        c.drawText(mListener.getTypeText(position), child.getPaddingLeft(), baseline, mTextPaint);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {//最后调用 绘制在最上层
        int pos = ((LinearLayoutManager)(parent.getLayoutManager())).findFirstVisibleItemPosition();

        String tag = mListener.getTypeText(pos);

        View child = parent.getChildAt(0);

        if(mListener.getType(pos)!= mListener.getType(pos+1)){
            //这是最后一个
            int bottom = child.getBottom();
            if(bottom<mTitleHeight){
                //底部小于分隔栏的高度了  说明已经上去了
                //背景
                c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop()+bottom, mPaint);
                mPaint.getTextBounds(tag, 0, tag.length(), mBounds);

                //文字
                Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
                int baseline = (bottom + bottom -mTitleHeight - fontMetrics.bottom - fontMetrics.top) / 2;
                c.drawText(tag,child.getPaddingLeft(),baseline,mTextPaint);
                return;
            }
        }

        //背景
        c.drawRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + mTitleHeight, mPaint);

        //字体
        mTextPaint.getTextBounds(tag, 0, tag.length(), mBounds);
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int baseline = (mTitleHeight - fontMetrics.bottom - fontMetrics.top) / 2;
        c.drawText(tag, child.getPaddingLeft(),
                baseline,
                mTextPaint);

    }
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static class Builder{

        private FloatingDecoration f;

        public Builder(Context context,DecorationListener listener){
            f = new FloatingDecoration(context,listener);
        }

        public Builder setTextSizeBySp(int sp){
            f.mTextPaint.setTextSize(sp2px(f.mContext,sp));
            return this;
        }

        public Builder setTextColor(int color){
            f.mTextPaint.setColor(color);
            return this;
        }

        public Builder setBackgroundColor(int color){
            f.mPaint.setColor(color);
            return this;
        }

        public Builder setHeight(int height){
            f.mTitleHeight = height;
            return this;
        }

        public FloatingDecoration build(){
            return f;
        }

    }
}

