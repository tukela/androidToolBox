/*
package com.honglei.toolbox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;
import com.zs.xgq.callback.OnScrollViewAspectListion;
import com.zs.xgq.callback.ScrollViewBottom;



public class MyScrollView extends ScrollView {
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledTopToBottom = true;
    private boolean isTouch = false;
    private OnScrollViewAspectListion aspectListion;
    private ScrollViewBottom scListion;

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public void setAspectListion(OnScrollViewAspectListion aspectListion) {
        this.aspectListion = aspectListion;
    }
    public void setScrollViewBottomListion(ScrollViewBottom scListion) {
        this.scListion = scListion;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (getScrollY() == 0) {
            isScrolledToTop = true;
        }  else {
            isScrolledToTop = false;
        }

        if (oldt > t) {// 向下
            isScrolledTopToBottom = true;
        } else if (oldt < t) {// 向上
            isScrolledTopToBottom = false;
        }
        Log.e("oldt ",""+oldt);
        if (oldt >= 1111){//留言板是否显示
            aspectListion.toTopVisibleView();
        }else {
            aspectListion.toTophintView();
        }
        if (getScrollY() + getHeight() - getPaddingTop()-getPaddingBottom() == getChildAt(0).getHeight()){
            scListion.isBottom();
        }

        notifyScrollChangedListener(getScrollY());
    }

    private void notifyScrollChangedListeners(int startRange,int endRange) {
//        Log.e("======","isScrolledToTop "+isScrolledToTop+" isScrolledTopToBottom"+isScrolledTopToBottom);
        if (isScrolledToTop && isScrolledTopToBottom) {
            //顶部 向下滑
            if (aspectListion != null) {
                aspectListion.toBotttom(startRange,endRange);
            }
        }
    }

    */
/**
     * 判断顶端布局是否可见
     * @param currentY
     *//*

    private void notifyScrollChangedListener(int currentY) {
//        Log.e("======","isScrolledToTop "+isScrolledToTop+" isScrolledTopToBottom"+isScrolledTopToBottom);
        if (aspectListion != null) {
            aspectListion.onScroll(currentY);
        }
    }

    int startY,endY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endY = (int) event.getY();
//                Log.e("onTouchEvent" , "Scrollevent_range "+(int) (endY - startY));

                break;
            case MotionEvent.ACTION_UP:
                if ((endY - startY )>0) {// 向下
                    isScrolledTopToBottom = true;
                } else{// 向上
                    isScrolledTopToBottom = false;
                }
                notifyScrollChangedListeners(startY,endY);
                break;
        }

        return super.onTouchEvent(event);
    }
}
*/
