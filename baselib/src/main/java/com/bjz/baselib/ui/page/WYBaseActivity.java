package com.bjz.baselib.ui.page;

import android.view.KeyEvent;
import android.view.View;

import com.wy.viewFrame.util.WYPageAnimUtils;
import com.wy.viewFrame.util.WYToast;
import com.wy.viewFrame.wyMainPart.WYBasePresenter;

/**
 * ==================================
 * Created by 边江洲 on 2018/11/9.
 * 作    者：WY_BJZ
 * 创建时间：2018/11/9
 * ==================================
 */
/*
 类 说 明：

 父页面的配置  控制类
 
 参数描述：
 
 
*/
public abstract class WYBaseActivity extends TMBaseShowAndInteractionActivity { //

/* ****************************************************** 动画类型配置 ****************************************************** */

    /* 页面动画类型 */
    private String animType = ANIM_TYPE_DEFAIL;

    @Override
    public void activityAnim(String animType) {
        this.animType = animType;
        switch (animType) {
            case ANIM_TYPE_DEFAIL:
                WYPageAnimUtils.skipDefailAnim(this);
                break;
            case ANIM_TYPE_FROM_TOP:
                WYPageAnimUtils.skipFromTop(this);
                break;
            case ANIM_TYPE_FROM_BOTTOM:
                WYPageAnimUtils.skipFromBottom(this);
                break;
            case ANIM_TYPE_FROM_ALPHA:
                WYPageAnimUtils.skipAlphAnim(this);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            switch (animType) {
                case ANIM_TYPE_DEFAIL:
                    WYPageAnimUtils.finishDefailAnim(this);
                    break;
                case ANIM_TYPE_FROM_TOP:
                    WYPageAnimUtils.closeToTop(this);
                    break;
                case ANIM_TYPE_FROM_BOTTOM:
                    WYPageAnimUtils.closeToBottom(this);
                    break;
                case ANIM_TYPE_FROM_ALPHA:
                    WYPageAnimUtils.closeAlphAnim(this);
                    break;
                default:
                    break;
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

/* ****************************************************** 控制器配置 ****************************************************** */

    @Override
    public WYBasePresenter getPresenter() {
        return null;
    }

/* ****************************************************** 页面操作配置 ****************************************************** */

    @Override
    public boolean isPuch() {
        return true;
    }

    @Override
    public boolean isTouchHideKeyBroad() {
        return false;
    }

/* ****************************************************** 页面展示配置 ****************************************************** */

    @Override
    public boolean isAddTopView() {
        return true;
    }

    @Override
    public boolean isAddTitleView() {
        return true;
    }

    @Override
    public boolean isImersive() {
        return true;
    }

    @Override
    public boolean isImersiveDark() {
        return true;
    }

/* ****************************************************** 页面显示配置 ****************************************************** */

    /**
     * 显示toast
     */
    public void showToast(String str) {
        WYToast.showShortToast(this, str);
    }

    /* 显示加载弹窗 */
    @Override
    public void showLoading(View view, String x) {

    }

    @Override
    public void showLoading(View view) {

    }

    @Override
    public void hideLoad() {

    }
}
