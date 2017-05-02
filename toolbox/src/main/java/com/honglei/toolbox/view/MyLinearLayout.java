package com.honglei.toolbox.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float startY = 0, startX, endY, endX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                startX = ev.getX();
//                Log.e("MyLinearLayout", "MyLinearLayout_ACTION_DOWN" + " startY " + startY + "startX " + startX);

                break;
            case MotionEvent.ACTION_MOVE:
                endY = ev.getY();
                endX = ev.getX();
                float y = endY - startY;
                float x = endX - startX;
                if (Math.abs(y) > Math.abs(x)) {
//                    Log.e("MyLinearLayout", "MyLinearLayout_ACTION_MOVE"+"上下滑动");
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
//                    Log.e("MyLinearLayout", "MyLinearLayout_ACTION_MOVE"+"水平滑动");
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e("MyLinearLayout", "MyLinearLayout_ACTION_UP");
                break;

        }


        return super.dispatchTouchEvent(ev);
    }
}
