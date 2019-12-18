package com.bjz.baselib.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bjz.baselib.IJZBaseView;
import com.bjz.baselib.JZBasePresenter;
import com.bjz.baselib.bean.JZLoadingBean;
import com.bjz.baselib.bean.JZPageData;
import com.bjz.baselib.JZPageLeftCycle;
import com.bjz.baselib.utils.JZToast;

import java.io.Serializable;

/**
 * Created by 边江洲 on 2017/11/1.
 */

public abstract class JZBasicFragment<T extends JZBasePresenter> extends Fragment implements IJZBaseView {

    public static <F extends JZBasicFragment> F getInstance(JZPageData data) {
        F fragment = (F) new Fragment();
        Bundle bundle = new Bundle();
        if (data != null && data.getKeys() != null) {
            for (String k : data.getKeys()) {
                Object obj = data.getV(k);
                if (obj instanceof Integer) {
                    bundle.putInt(k, (int) obj);
                } else if (obj instanceof Double) {
                    bundle.putDouble(k, (double) obj);
                } else if (obj instanceof Float) {
                    bundle.putFloat(k, (float) obj);
                } else if (obj instanceof Long) {
                    bundle.putLong(k, (long) obj);
                } else if (obj instanceof String) {
                    bundle.putString(k, (String) obj);
                } else if (obj instanceof Boolean) {
                    bundle.putBoolean(k, (boolean) obj);
                } else {
                    bundle.putSerializable(k, (Serializable) obj);
                }
            }
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    public View view;

    public Context mContext;

    private JZPageLeftCycle pageLeftCycle;
    public boolean isPause = false;
    public boolean isStop = false;
    public boolean isDestory = false;
    public boolean isFristInto = false;

    T presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getActivity();
        pageLeftCycle = new JZPageLeftCycle();
    }

    @Override
    public void onStart() {
        super.onStart();
        pageLeftCycle.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = getPresenter();
        pageLeftCycle.onStart();

        if (getLayoutRes() != 0) {
            view = inflater.inflate(getLayoutRes(), container, false);
        } else if (getCustomView() != null) {
            view = getCustomView();
        } else {
            new RuntimeException("please resturn getLayoutRes() or getCustomView()");
        }
        init();
        initView();
        initData();
        setListener();
        return view;
    }

    public <viewT extends View> viewT bind(int id) {
        return (viewT) view.findViewById(id);
    }

    public abstract int getLayoutRes();

    public View getCustomView() {
        return null;
    }

    public abstract void init();

    public abstract void initView();

    public abstract void initData();

    public abstract void setListener();

    /**
     * 显示加载
     */
    @Override
    public void showLoad(JZLoadingBean loadingBean) {
    }

    /**
     * 隐藏加载
     */
    @Override
    public void hideLoad() {
    }

    @Override
    public void onResume() {
        super.onResume();
        isPause = false;
        isStop = false;
        isDestory = false;
        pageLeftCycle.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
        if (isFristInto) {
            isFristInto = false;
        }
        pageLeftCycle.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        isStop = true;
        pageLeftCycle.onStop();
    }

    public abstract T getPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestory = true;
        pageLeftCycle.onDestory(presenter, mContext);
    }

    /**
     * 显示toast
     */
    public void showToast(String str) {
        JZToast.showShortToast(mContext, str);
    }

}
