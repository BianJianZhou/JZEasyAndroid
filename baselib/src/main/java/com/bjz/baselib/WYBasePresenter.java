package com.bjz.baselib;

import com.bjz.baselib.event.JZCallBack;
import com.bjz.baselib.event.JZCallBackDataVo;
import com.bjz.baselib.event.JZOneStatusCallBack;

/**
 * ==================================
 * Created by 边江洲 on 2018/5/12.
 * 作    者：WY_BJZ
 * 创建时间：2018/5/12
 * ==================================
 */
/*
 类 说 明：
 
 参数描述：
 

 此类处理网络连接判断，进行断网判断提示，网络状态改变提示，回调到 baseActivity 中进行显示，断网重连，当前页面 接口重新请求

 网络判断在这里做，每次请求进行判断，断网回调在Activity中做提示，

在调用request 前 先进行总的网络判断  是否有网 （ping） 无网络状态下，页面的友好提示

*/
public class WYBasePresenter {

//    public IWYBaseView iwyBaseView;
//
//    public Context context;
//
//    public WYBasePresenter(IWYBaseView iwyBaseView, Context context) {
//        this.iwyBaseView = iwyBaseView;
//        this.context = context;
//        JZSingleCallBack.regist("myasdfasd", (JZOneStatusCallBack<JZCallBackDataVo<String>>) result -> {
//
//        });
//        JZSingleCallBack.regist("myasdfasd", (JZOneStatusCallBack<JZCallBackDataVo<String>>) result -> {
//
//        });
//
////        JZCallBackDataVo<String> jzEventVo = new JZCallBackDataVo<>();
////        jzEventVo.setData("change_type_1");
//
//    }

    public static void regist() {
        JZCallBack.single("test").regist("myasdfasd", (JZOneStatusCallBack<JZCallBackDataVo<String>>) result -> {
            System.out.println(result.getResult());
        });
    }

//    public void onDestory() {
//        iwyBaseView = null;
//    }

}
