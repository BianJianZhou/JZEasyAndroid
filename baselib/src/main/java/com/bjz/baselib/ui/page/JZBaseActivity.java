package com.bjz.baselib.ui.page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bjz.baselib.IJZBaseView;
import com.bjz.baselib.JZBasePresenter;
import com.bjz.baselib.JZPageLeftCycle;
import com.bjz.baselib.R;
import com.bjz.baselib.bean.JZLoadingBean;
import com.bjz.baselib.bean.JZPageData;
import com.bjz.baselib.event.JZOneStatusCallBack;
import com.bjz.baselib.utils.AndroidBottomBarAdaptive;
import com.bjz.baselib.utils.JZLog;
import com.bjz.baselib.utils.JZPageConfig;
import com.bjz.baselib.utils.JZToast;
import com.bjz.baselib.utils.JZUtil;
import com.bjz.baselib.utils.stateBar.ImmersionBar;
import com.bjz.baselib.widget.JZTitleView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ==================================
 * Created by 边江洲 on 2018/9/7.
 * 作    者：WY_BJZ
 * 创建时间：2018/9/7
 * ==================================
 */
/*
 类 说 明：

 页面组件详细逻辑层

 参数描述：


*/
public abstract class JZBaseActivity<T extends JZBasePresenter> extends Activity implements IJZBaseView {

    /* 生命周期辅助类 */
    private JZPageLeftCycle pageLeftCycle;

    /* 页面配置 */
    public JZPageConfig
            jzPageConfig;

    public boolean
            isResume = false,
            isPause = false,
            isStop = false,
            isDestory = false;

    /* 是否第一次进入页面 */
    public boolean isFirstInto = true;

    /* 手指按压初始坐标 */
    float startX = 0;
    float startY = 0;
    long pushAnimBackDuration = 300;

    /* 执行色值过度的类 */
    ArgbEvaluator dragArgbEvaluator = null;

    /* 屏幕的宽高 */
    int
            screenW,
            screenH;

    T presenter;

    /* 组件相关 */
    private View top1PxView = null;
    /* 最外层容器 */
    RelativeLayout bigGroupView = null;
    /* res 加载容器 */
    LinearLayout resViewGroupView = null;
    /* 左侧点击遮盖view */
    View leftClickCoverView = null;
    /* layout组件 */
    View view;
    /* 默认titleView */
    JZTitleView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /* 生命周期辅助类 */
        pageLeftCycle = new JZPageLeftCycle();
        presenter = getPresenter();
        JZPageConfig pageConfigTemp = getJzPageConfig();
        if (pageConfigTemp != null) {
            jzPageConfig = pageConfigTemp;
        } else {
            jzPageConfig = JZPageConfig.getInstance();
        }
        isResume = false;
        isPause = false;
        isStop = false;
        isDestory = false;
        super.onCreate(savedInstanceState);
        /* 限制只能竖屏显示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dragArgbEvaluator = new ArgbEvaluator();
        screenW = JZUtil.screenWidth(this);
        screenH = JZUtil.screenHeigth(this);

        bigGroupView = new RelativeLayout(this);
        bigGroupView.setBackgroundResource(setBigGroupBk());
        /* 添加顶部1px横线 */
        top1PxView = new View(this);
        bigGroupView.addView(top1PxView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dimens_1)));
        /* 添加加载展示组件的逻辑 */
        resViewGroupView = new LinearLayout(this);
        resViewGroupView.setOrientation(LinearLayout.VERTICAL);
        bigGroupView.addView(resViewGroupView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        /* ****************************************************** 加载展示组件逻辑 ****************************************************** */
        /* 判断添加topView */
        if (jzPageConfig.isAddTopView()) {
            View titleTopView = new View(this);
            resViewGroupView.addView(titleTopView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, JZUtil.getStatusBarHeight(this)));
            titleTopView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        /* 判断添加默认titleView */
        if (jzPageConfig.isAddTitleView()) {
            titleView = new JZTitleView(this);
            RelativeLayout.LayoutParams titleViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (jzPageConfig.isAddTopView() && !jzPageConfig.isAddTitleView()) {
                titleViewParams.setMargins(0, JZUtil.getStatusBarHeight(this), 0, 0);
            }
            resViewGroupView.addView(titleView, titleViewParams);
        }
        /* 页面展示组件 */
        if (getResId() != 0) {
            view = LayoutInflater.from(this).inflate(getResId(), null);
        } else if (getCustomView() != null) {
            view = getCustomView();
        }
        resViewGroupView.addView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        /* 添加左侧点击遮盖布局 -- 用来做拖动页面退出操作 */
        leftClickCoverView = new View(this);
        /* 用来拦截此区域的点击事件 */
        leftClickCoverView.setOnClickListener(v -> {
        });
        bigGroupView.addView(leftClickCoverView, new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.dimens_20), RelativeLayout.LayoutParams.MATCH_PARENT));
        /* 容器加载到 页面中 */
        setContentView(bigGroupView);
        /* 用来做虚拟物理按键的适配 */
        AndroidBottomBarAdaptive.assistActivity(this, bigGroupView);

        /* 设置沉浸式状态栏 */
        if (jzPageConfig.isImersive()) {
            ImmersionBar
                    .with(this)
                    .init();
        }
        /* 状态栏内容是否深色 */
        if (jzPageConfig.isImersiveDark()) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .statusBarDarkFont(false)
                    .init();
        }
    }

    public abstract int getResId();

    public View getCustomView() {
        return null;
    }

    public JZPageConfig getJzPageConfig() {
        return null;
    }

    public abstract T getPresenter();

    /* 请求线程列表 */
    /* k：标识不同的接口 */
    /* v：存储请求接口所用线程 */
    public Map<String, Runnable> requestRunMap = new HashMap<>();

    public Runnable testRun = () -> runOnUiThread(() -> {
        JZLog.d("检查", "线程请求模拟1");
    });

    /* 页面跳转 */
    public void jumpNextPage(JZPageData pageData, Class activity) {
        Intent intent = new Intent();
        intent.setClass(this, activity);
        if (pageData.getKeys() != null && pageData.getKeys().size() > 0) {
            for (String k : pageData.getKeys()) {
                Object v = pageData.getV(k);
                if (v instanceof Integer) {
                    intent.putExtra(k, (int) v);
                } else if (v instanceof Double) {
                    intent.putExtra(k, (double) v);
                } else if (v instanceof Float) {
                    intent.putExtra(k, (float) v);
                } else if (v instanceof Long) {
                    intent.putExtra(k, (long) v);
                } else if (v instanceof String) {
                    intent.putExtra(k, (String) v);
                } else if (v instanceof Boolean) {
                    intent.putExtra(k, (boolean) v);
                } else {
                    intent.putExtra(k, (Serializable) v);
                }
            }
        }
        if (pageData.getIntentFlag() != 0) {
            intent.setFlags(pageData.getIntentFlag());
        }
        startActivity(intent);
    }

    /* 用来处理当存在虚拟物理按键布局异常问题 */
    boolean isBottomBarDispose = false;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isBottomBarDispose) {
            isBottomBarDispose = true;
            AndroidBottomBarAdaptive.assistActivity(this, bigGroupView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
        isPause = false;
        isStop = false;
        isDestory = false;
        pageLeftCycle.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isResume = false;
        isPause = true;
        isStop = false;
        isDestory = false;
        pageLeftCycle.onPause();
        postDelayed(300, result -> {
            if (isFirstInto) {
                isFirstInto = false;
            }
        });
    }

    /* 延时方法调用 */
    public void postDelayed(long time, JZOneStatusCallBack oneStatusCallBack) {
        bigGroupView.postDelayed(() -> {
            if (oneStatusCallBack != null) {
                oneStatusCallBack.result("");
            }
        }, time);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
        isPause = false;
        isStop = true;
        isDestory = false;
        pageLeftCycle.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isResume = false;
        isPause = false;
        isStop = false;
        isDestory = true;
        pageLeftCycle.onDestory(presenter, this);
    }

    public <viewT extends View> viewT bind(int id) {
        return (viewT) view.findViewById(id);
    }

    public int setBigGroupBk() {
        return Color.parseColor("#ffffff");
    }

    /* 显示加载圈 */
    @Override
    public void showLoad(JZLoadingBean loadingBean) {

    }

    /* 关闭加载圈 */
    @Override
    public void hideLoad() {

    }

    public void showToast(String str) {
        JZToast.showShortToast(this, str);
    }

    /* ****************************************************** 页面拖动退出逻辑 ****************************************************** */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (jzPageConfig.isDragFinish()) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                startX = ev.getX();
                startY = ev.getY();
            } else if (startX < getResources().getDimension(R.dimen.dimens_20) && ev.getAction() == MotionEvent.ACTION_MOVE) {
                float moveDistanceX = ev.getX() - startX;
                if (moveDistanceX > 0) {
                    resViewGroupView.setX(moveDistanceX);

                    /* 随移动距离透明度改变 */
                    int bgColor = (int) dragArgbEvaluator.evaluate(
                            (moveDistanceX / (float) (screenW / 3)) > 1 ? 1 : moveDistanceX / (float) (screenW / 3), 0Xcc000000, 0X00000000);
                    bigGroupView.setBackgroundColor(bgColor);

                    return true;
                }

            } else if (startX < getResources().getDimension(R.dimen.dimens_20) && ev.getAction() == MotionEvent.ACTION_UP) {
                float moveDistanceX = ev.getX() - startX;
                /* 必须向右滑动才生效 */
                if (ev.getX() - startX > 0) {
                    if (moveDistanceX > JZUtil.screenWidth(this) / 3) {
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

        if (jzPageConfig.isTouchHideKeyBroad()) {
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
        ValueAnimator anim = ValueAnimator.ofFloat(moveDistanceX, JZUtil.screenWidth(this));
        anim.setDuration(300); // 一秒的时间结束, 为了简单这里固定为1秒
        anim.start();

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 位移
                float x = (float) (animation.getAnimatedValue());
                resViewGroupView.setX(x);
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
        ObjectAnimator.ofFloat(resViewGroupView, "X", moveDistanceX, 0).setDuration(pushAnimBackDuration).start();
        postDelayed(pushAnimBackDuration, result -> {
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
