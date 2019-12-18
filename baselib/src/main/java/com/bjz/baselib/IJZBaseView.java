package com.bjz.baselib;

import android.view.View;

import com.bjz.baselib.bean.JZLoadingBean;

public interface IJZBaseView {

    void showLoad(JZLoadingBean loadingBean);

    void hideLoad();

}
