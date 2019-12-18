package com.bjz.baselib.ui.page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.wy.viewFrame.R;
import com.wy.viewFrame.util.Utils;
import com.wy.viewFrame.wyMainPart.listener.WYResultListener;

/**
 * ==================================
 * Created by 边江洲 on 2018/11/9.
 * 作    者：WY_BJZ
 * 创建时间：2018/11/9
 * ==================================
 */
/*
 类 说 明：

 页面展示 和 交互层
 
 参数描述：
 
 
*/
public abstract class TMBaseShowAndInteractionActivity extends WYBaseLogicActivity {

    long pushAnimBackDuration = 300;

    /* 延时方法调用 */
    @Override
    public void postDelayed(long duration, WYResultListener listener) {
        showGroupView.postDelayed(() -> {
            if (listener != null) {
                listener.result();
            }
        }, duration);
    }

/* ****************************************************** 页面拖动退出逻辑 ****************************************************** */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isPuch()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                startX = ev.getX();
                startY = ev.getY();
            } else if (startX < getResources().getDimension(R.dimen.dimen_20_dip) && ev.getAction() == MotionEvent.ACTION_MOVE) {
                float moveDistanceX = ev.getX() - startX;
                if (moveDistanceX > 0) {
                    showGroupView.setX(moveDistanceX);

                    /* 随移动距离透明度改变 */
                    int bgColor = (int) dragArgbEvaluator.evaluate(
                            (moveDistanceX / (float) (screenW / 3)) > 1 ? 1 : moveDistanceX / (float) (screenW / 3), 0Xcc000000, 0X00000000);
                    bigGroupView.setBackgroundColor(bgColor);

                    return true;
                }

            } else if (startX < getResources().getDimension(R.dimen.dimen_20_dip) && ev.getAction() == MotionEvent.ACTION_UP) {
                float moveDistanceX = ev.getX() - startX;
                /* 必须向右滑动才生效 */
                if (ev.getX() - startX > 0) {
                    if (moveDistanceX > Utils.screenWidth(this) / 3) {
                        // 如果滑动的距离超过了手机屏幕的一半, 滑动处屏幕后再结束当前Activity
                        continueMove(moveDistanceX);
                    } else {
                        // 如果滑动距离没有超过一半, 往回滑动
                        rebackToLeft(moveDistanceX);
                    }
                }
            }
        }

/* ****************************************************** 页面点击其他地方输入框隐藏逻辑 ****************************************************** */

        if (isTouchHideKeyBroad()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (isShouldHideInput(v, ev)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 从当前位置一直往右滑动到消失。
     * 这里使用了属性动画。
     */
    private void continueMove(float moveDistanceX) {
        // 从当前位置移动到右侧。
        ValueAnimator anim = ValueAnimator.ofFloat(moveDistanceX, Utils.screenWidth(this));
        anim.setDuration(300); // 一秒的时间结束, 为了简单这里固定为1秒
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 位移
                float x = (float) (animation.getAnimatedValue());
                showGroupView.setX(x);
            }
        });

        // 动画结束时结束当前Activity
        anim.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
            }
        });
    }

    /**
     * Activity被滑动到中途时，滑回去~
     */
    private void rebackToLeft(float moveDistanceX) {
        ObjectAnimator.ofFloat(showGroupView, "X", moveDistanceX, 0).setDuration(pushAnimBackDuration).start();
        postDelayed(pushAnimBackDuration, () -> {
            bigGroupView.setBackgroundResource(setBigGroupBk());
        });
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
