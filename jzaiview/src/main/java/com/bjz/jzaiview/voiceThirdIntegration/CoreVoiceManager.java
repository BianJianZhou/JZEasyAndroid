package com.bjz.jzaiview.voiceThirdIntegration;

/*
对于第三方的交互而言需要的：
1: 控制语音sdk的 初始化，打开，关闭
2：语音结果反馈
3：网络状态（断网情况下的本地识别，联网识别）
4：因为只是添加语音功能的第三方框架，所以没有必要支持语音唤醒（需要本人去第三方平台申请唤醒词的，会使使用场景变得复杂）
*/

/*
需要做一个本地存储热词的 工具类
可选方式，share 或者 存储至文件夹
*/
public interface CoreVoiceManager {

    /* 识别模式 */
    int RECOGNITION_MODE_ACCURATE = 000001;
    int RECOGNITION_MODE_INDISTINCT = 000002;
    /* view类型 */
    /* 目前只做了四种支持 */
    int VIEW_MODE_TEXT = 010001;
    int VIEW_MODE_BUTTON = 010002;
    int VIEW_MODE_SCROLL = 010003;
    int VIEW_MODE_RECYCLER = 010004;

    /*                                       与第三方语音框架的交互                                       */
    /* 语音的初始化 */
    void voiceSdkInitialize();

    /* 关闭语音识别 */
    void open();

    /* 打开语音识别 */
    void close();

    /* 释放 */
    void destory();

    /* 处理结果返回 */
    /* 这里我们通过一种解耦的接口或者监听来实现，观察者模式，handler注册，单例监听 中可以挑选一种 */
    /* 在这里面还会设计一种全局注册监听，使用Str字符为表示进行通信的方式，依赖于整体框架本身 */
    void callback(String recognitionResultStr);

    /*                                       用户与本框架的交互                                       */
    /* 这里应该在于 sdk中进行设置 */
    /* 返回当前识别模式 */
    int recognitionMode();
    /* 识别模式设置 请取值本类静态常量*/
    void setRecognitionMode(int recognitionMode);
    /* 注册热词 */
    void registHotWord(int viewMode,String hotWord);
    /* 获取热词 （这里还没有更好的思路，文本类的 固定的 查看详情，确认其他操作暂未考虑，按钮类的自带文本 内容识别，限制 4 字符以内（可变）） */
    /* text view 在设计之初应该，初始化的时候应该 添加一种功能，将textview注册为button类型，并附带 识别热词，识别热词 推荐 仿js string 对象类型或者，对象（bean为：热词列表，识别模式--》
                                                                                                                每个view在注册每个热词的时候可以单独指定每个热词的识别模式） */
    ViewHotMode viewHotWord(ViewHotMode viewHotMode);


}
