package com.ice.materiadesign.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by asd on 2/16/2017.
 */
//泛型为目标view的类型 即app:layout_behavior
public class FloatingActionBarAutoHideBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {

    //必须重写带有两个参数的构造方法
    //因为 CoordinatorLayout 里面是通过反射来实例化 Behavior 对象的,
    // 而反射的时候使用了这个带两个参数的构造器来实例化的.
    public FloatingActionBarAutoHideBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     *
     CoordinatorLayout, 即parentView,
     child, 即Behavior对应的这个ChildView, 这里是 FloatingActionButton
     target, 即触发嵌套滑动的子View, 这里是 RecyclerView
     dxConsumed, 横向滑动距离
     dyConsumed, 纵向滑动距离
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child, View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        if (dyConsumed > 0) {
            // 手势从下向上滑动(列表往下滚动), 隐藏
            CoordinatorLayout.LayoutParams layoutParams =
                    (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int fab_bottomMargin = layoutParams.bottomMargin;
            setAnimateTranslationY(child, child.getHeight() + fab_bottomMargin);
        } else if (dyConsumed < 0) {
            // 手势从上向下滑动(列表往上滚动), 显示
            setAnimateTranslationY(child, 0);
        }
    }

    private void setAnimateTranslationY(View view, int y) {
        view.animate().translationY(y).setInterpolator(new LinearInterpolator()).start();
    }

}
