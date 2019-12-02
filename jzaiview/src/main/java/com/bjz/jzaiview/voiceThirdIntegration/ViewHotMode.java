package com.bjz.jzaiview.voiceThirdIntegration;

import java.util.List;

public class ViewHotMode {
    int viewMode;
    List<HotWord> hotWordArr;


}

class HotWord{
    String hotWord;
    int recognitionMode;//这里应该赋值等于 设置的默认类型(还需要一个类提前获取存储的值，避免频繁获取)

    public int getRcognitionMode(){
        return recognitionMode;
    }

    public void recognitionMode(int recognitionMode){
        this.recognitionMode = recognitionMode;
    }

}
