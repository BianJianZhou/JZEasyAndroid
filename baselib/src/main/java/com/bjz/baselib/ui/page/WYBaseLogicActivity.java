package com.bjz.baselib.ui.page;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wy.viewFrame.R;
import com.wy.viewFrame.statebar.AndroidBottomBarAdaptive;
import com.wy.viewFrame.statebar.barlibrary.ImmersionBar;
import com.wy.viewFrame.util.LogUtils;
import com.wy.viewFrame.util.Utils;
import com.wy.viewFrame.vo.WYRequestArrayVo;
import com.wy.viewFrame.wyMainPart.WYBasePresenter;
import com.wy.viewFrame.wyMainPart.api.IWYPageApi;
import com.wy.viewFrame.wyMainPart.api.WYPageLeftCycle;
import com.wy.viewFrame.wyMainPart.listener.IWYBaseView;
import com.wy.viewFrame.wyMainPart.listener.WYListener;
import com.wy.viewFrame.wyMainPart.view.WYTitleView;

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
public abstract class WYBaseLogicActivity extends Activity implements IWYPageApi, IWYBaseView {

    /* 生命周期辅助类 */
    private WYPageLeftCycle pageLeftCycle;
    /* 是否离开了页面 */
    public boolean isLeave = true;
    /* 是否第一次进入页面 */
    public boolean isFirstInto = true;
    /* 是否销毁页面 */
    public boolean isDestory = true;

    /* 手指按压初始坐标 */
    float startX = 0;
    float startY = 0;

    /* 执行色值过度的类 */
    ArgbEvaluator dragArgbEvaluator = null;
    /* 屏幕的宽高 */
    int
            screenW,
            screenH;

    WYBasePresenter basePresenter;

    /* 组件相关 */
    private View top1PxView = null;
    /* 最外层容器 */
    RelativeLayout bigGroupView = null;
    /* 展示组件加载容器 */
    LinearLayout showGroupView = null;
    /* 左侧点击遮盖view */
    View leftClickCoverView = null;
    /* layout组件 */
    View resLayout;
    /* 默认titleView */
    WYTitleView titleView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        isDestory = false;
        super.onCreate(savedInstanceState);
        /* 限制只能竖屏显示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dragArgbEvaluator = new ArgbEvaluator();
        screenW = Utils.screenWidth(this);
        screenH = Utils.screenHeigth(this);

        bigGroupView = new RelativeLayout(this);
        bigGroupView.setBackgroundResource(setBigGroupBk());
        /* 添加顶部1px横线 */
        top1PxView = new View(this);
        bigGroupView.addView(top1PxView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.dimen_1_dip)));
        /* 添加加载展示组件的逻辑 */
        showGroupView = new LinearLayout(this);
        bigGroupView.addView(showGroupView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
        showGroupView.setOrientation(LinearLayout.VERTICAL);

        /* ****************************************************** 加载展示组件逻辑 ****************************************************** */
        /* 判断添加topView */
        if (isAddTopView()) {
            View titleTopView = new View(this);
            showGroupView.addView(titleTopView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight()));
            titleTopView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        /* 判断添加默认titleView */
        if (isAddTitleView()) {
            titleView = new WYTitleView(this);
            RelativeLayout.LayoutParams titleViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (isAddTopView() && !isAddTitleView()) {
                titleViewParams.setMargins(0, getStatusBarHeight(), 0, 0);
            }
            showGroupView.addView(titleView, titleViewParams);
        }
        /* 页面展示组件 */
        if (getLayoutResId() != 0) {
            resLayout = LayoutInflater.from(this).inflate(getLayoutResId(), null);
        } else if (getShowGroupView() != null) {
            resLayout = getShowGroupView();
        }
        showGroupView.addView(resLayout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        /* 添加左侧点击遮盖布局 -- 用来做拖动页面退出操作 */
        leftClickCoverView = new View(this);
        /* 用来拦截此区域的点击事件 */
        leftClickCoverView.setOnClickListener(v -> {
        });
        bigGroupView.addView(leftClickCoverView, new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.dimen_20_dip), RelativeLayout.LayoutParams.MATCH_PARENT));
        /* 最左侧阴影布局 */
//        View leftShadowView = new View(this);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.dimen_20_dip), RelativeLayout.LayoutParams.MATCH_PARENT);
//        params.setMargins((int) getResources().getDimension(R.dimen._dimen_20_dip), 0, 0, 0);
//        leftShadowView.setBackgroundResource(R.drawable.tm_base_left_shadow_view_bk);
//        bigGroupView.addView(leftShadowView, params);

        /* 容器加载到 页面中 */
        setContentView(bigGroupView);

        /* 用来做虚拟物理按键的适配 */
//        AndroidBottomBarAdaptive.assistActivity(this, bigGroupView);

        /* 设置沉浸式状态栏 */
        if (isImersive()) {
            ImmersionBar
                    .with(this)
                    .init();
        }
        /* 状态栏内容是否深色 */
        if (isImersiveDark()) {
            ImmersionBar.with(this)
                    .statusBarDarkFont(true)
                    .init();
        } else {
            ImmersionBar.with(this)
                    .statusBarDarkFont(false)
                    .init();
        }
        /* 生命周期辅助类 */
        pageLeftCycle = new WYPageLeftCycle();
        basePresenter = getPresenter();

        /* 用户的页面初始化 */
        init(savedInstanceState);
        /* 用来判断请求成功失败 */
        requestData(new WYListener<WYRequestArrayVo>() {
            /* 成功 */
            @Override
            public void ok(WYRequestArrayVo wyRequestArrayVo) {

            }

            /* 失败 */
            @Override
            public void fail(String code, String failMsg, Throwable e) {

            }
        });


    }

    /* 请求线程列表 */
    /* k：标识不同的接口 */
    /* v：存储请求接口所用线程 */
    public Map<String, Runnable> requestRunMap = new HashMap<>();

    public Runnable testRun = () -> runOnUiThread(() -> {
        LogUtils.d("检查", "线程请求模拟1");
    });

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
        pageLeftCycle.onResume();
        isLeave = false;
        hideLoad();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pageLeftCycle.onPause();
        isLeave = true;
        postDelayed(300, () -> {
            if (isFirstInto) {
                isFirstInto = false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        pageLeftCycle.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestory = true;
        pageLeftCycle.onDestory(basePresenter, this);
    }

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = -1;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public <viewT extends View> viewT bind(int id) {
        return (viewT) resLayout.findViewById(id);
    }

    /* ****************************************************** 获取页面组件 ****************************************************** */

    @Override
    public View getShowGroupView() {
        return null;
    }

    @Override
    public View getTop1pxView() {
        return top1PxView;
    }

    @Override
    public int setBigGroupBk() {
        return R.color.white;
    }

    @Override
    public View getBigGroupView() {
        return bigGroupView;
    }

    @Override
    public WYTitleView getTitleView() {
        if (titleView == null) {
            throw new RuntimeException("请先设置 《WYDefailTitleView》：\n{ titleView为空 }");
        }
        return titleView;
    }
}
