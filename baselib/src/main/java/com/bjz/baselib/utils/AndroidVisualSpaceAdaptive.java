package com.bjz.baselib.utils;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class AndroidVisualSpaceAdaptive {

    View
            dectorView;

    Context
            context;

    public void AndroidVisualSpaceAdaptive(View dectorView, Context context) {
        this.dectorView = dectorView;
        this.context = context;
    }

    private void asdf() {
        dectorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getShowViewBottm();
            }
        });
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private int getShowViewBottm() {
        Rect r = new Rect();
        dectorView.getWindowVisibleDisplayFrame(r);
        return r.bottom;
    }

}
