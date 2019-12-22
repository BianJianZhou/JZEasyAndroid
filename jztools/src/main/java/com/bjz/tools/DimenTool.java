package com.bjz.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class DimenTool {

    /* 尺寸单位 */
    String unitStr = "px";
    /* dimens 名称 */
    String dimensName = "dimens_";// + i
    /* 标注分辨率 */
    private final double labelResolutionRatio = 750;
    /* 读取文件名称 */
    private String fileName = "dimens.xml";
    /* 读取文件路径 */
    private String filePath = "C:/ASWorkSpace/WYEasyAndroid/app/src/main/res/values";
    /* 生成dimens文件 中总 单位数量 从 1 到 n */
    private int dimensNum = 1440;


    /* dimens文件头部 */
    /* <?xml version="1.0" encoding="utf-8"?> */
    String headStr = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    /* 开始和结束标签 */
    String startLabel = "<resources>";
    String endLabel = "</resources>";
    /* 尺寸标签 */
    /* <dimen name="ydimen_1_dip">1.0px</dimen> */
    String dimenStartLabel = "<dimen name=\"";
    String dimenCenterStr = "\">";
    String dimenEndLabel = "</dimen>";

    /* 分辨率集合 */
    private List<ResolutionRatio> resolutionRatios = new ArrayList<>();
    /* 存储路径集合 */
    private List<String> pathList = new ArrayList<>();
    /* stringbuilder 集合 */
    private List<StringBuilder> stringBuilderList = new ArrayList<>();

    public void createOneDimensFile() {
        File pathFile = new File(filePath);
        File file = new File(filePath + "/" + fileName);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        /* 不存在则创建 dimens 文件 */
        if (!file.exists()) {
            try {
                file.createNewFile();
                writeIn();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            writeIn();
        }
    }

    private void writeIn() {
        StringBuilder builder = new StringBuilder();
        builder.append(headStr).append("\r\n")
                .append(startLabel).append("\n");
        for (int i = 1; i <= dimensNum; i++) {
            builder.append("    ").append(dimenStartLabel).append(dimensName).append(i).append(dimenCenterStr).append(i).append(unitStr).append(dimenEndLabel).append("\n");
        }
        for (int i = 1; i <= dimensNum; i++) {
            builder.append(dimenStartLabel).append(dimensName).append("fu_").append(i).append(dimenCenterStr).append("-").append(i).append(unitStr).append(dimenEndLabel).append("\n");
        }
        builder.append(endLabel);
        writeFile(filePath + "/" + fileName, builder.toString());
    }

    public void init() {
        /* 初始化分辨率 */
        resolutionRatios.add(new ResolutionRatio(1080, 1920));
        resolutionRatios.add(new ResolutionRatio(1080, 2160));
        resolutionRatios.add(new ResolutionRatio(1080, 2220));
        resolutionRatios.add(new ResolutionRatio(1440, 2560));
        resolutionRatios.add(new ResolutionRatio(1440, 2880));
        resolutionRatios.add(new ResolutionRatio(1440, 2960));
        for (int i = 0; i < resolutionRatios.size(); i++) {
            pathList.add(filePath + "-" + (int) resolutionRatios.get(i).y + "x" + (int) resolutionRatios.get(i).x);
            stringBuilderList.add(new StringBuilder());
        }
        readDimens();
    }

    private void readDimens() {
        File file = new File(filePath + "/" + fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    //截取<dimen></dimen>标签内的内容，从>右括号开始，到左括号减2，取得配置的数字
                    Double num = Double.parseDouble
                            (tempString.substring(tempString.indexOf(">") + 1,
                                    tempString.indexOf("</dimen>") - 2));
                    //根据不同的尺寸，计算新的值，拼接新的字符串，并且结尾处换行。
                    for (int i = 0; i < stringBuilderList.size(); i++) {
                        stringBuilderList.get(i).append(start).append(getDimens(num, resolutionRatios.get(i).x)).append(end).append("\r\n");
                    }
                } else {
                    for (int i = 0; i < stringBuilderList.size(); i++) {
                        stringBuilderList.get(i).append(tempString).append("");
                    }
                }
                line++;
            }
            for (int i = 0; i < pathList.size(); i++) {
                File fileTemp = new File(pathList.get(i));
                fileTemp.mkdirs();
                File dimensFile = new File(pathList.get(i) + "/" + fileName);
                dimensFile.createNewFile();
                writeFile(pathList.get(i) + "/" + fileName, stringBuilderList.get(i).toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 写入方法
     */
    private static void writeFile(String file, String text) {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }

    /**
     * 获取计算后的数值
     *
     * @param readDimens              读取的数值
     * @param adaptiveResolutionRatio 适配分辨率
     */
    private double getDimens(double readDimens, double adaptiveResolutionRatio) {
        double ratio = adaptiveResolutionRatio / labelResolutionRatio;
        double calculate = readDimens * ratio;
        double temp = new BigDecimal(calculate).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return temp;
    }

    private class ResolutionRatio {
        double x;
        double y;

        public ResolutionRatio(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
    }

}
