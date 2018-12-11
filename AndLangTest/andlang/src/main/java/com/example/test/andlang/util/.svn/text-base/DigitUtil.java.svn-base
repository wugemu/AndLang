package com.example.test.andlang.util;

import java.math.BigDecimal;

/**
 * Created by root on 18-4-12.
 */
//数字 格式化工具
public class DigitUtil {
    /**
     * 将double类型数据转换2位小数的税费价格
     *
     * @param d
     * @return
     */
    public static String getFloatFormat(float d) {

        return String.format("%.2f", getDoubleRoundOf(d));
    }

    /**
     * 四舍五入
     *
     * @param d
     * @return //先保留三位小数，再保留两位小数
     */
    public static double getDoubleRoundOf(float d) {
        BigDecimal bg = new BigDecimal(String.valueOf(d));
        d = bg.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
        d = new BigDecimal(String.valueOf(d)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return d;
    }
}
