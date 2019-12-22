package com.bjz.baselib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.reflect.Method;

/**
 * Created by 边江洲 on 2017/12/28.
 */

public class AndroidBottomBarAdaptive {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    /**
     * 关联要监听的视图
     *
     * @param viewObserving
     */
    public static void assistActivity(Context context, View viewObserving) {
        new AndroidBottomBarAdaptive(context, viewObserving);
    }

    private AndroidBottomBarAdaptive(Context context, View viewObserving) {
        restartViewHeight(context, viewObserving);
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private static int getShowViewBottm(View changeView) {
        Rect r = new Rect();
        changeView.getWindowVisibleDisplayFrame(r);
        return r.bottom;
    }

    private void restartViewHeight(Context context, View changeView) {
        if (JZUtil.hasNavBar(context)) {
            new Handler().postDelayed(() -> {
//                WYToast.showLongToast(context,
//                        "可视区域底部 -- >" + getShowViewBottm(changeView) + "" +
//                                "\n屏幕高 -- >" + Utils.screenHeigth(context) + "" +
//                                "\n组件底部位置 -- >" + changeView.getBottom() + ""
//                );  如果 不做延迟 组件底部的 changeView.getBottom() 的值与 上两个值是相同的
                if (getShowViewBottm(changeView) < changeView.getBottom()) {
                    changeView.setPadding(0, 0, 0, JZUtil.getNavigationBarHeight(context));
                    /* 底部虚拟按键显示 */
                } else {
                    /* 底部虚拟按键未显示 */
                }
            }, 30);
        }
    }

}

