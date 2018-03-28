package com.example.test.andlang.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Bill56 on 2018-1-12.
 */

public class RegexUtil {

    // 正则表达式
    // 特殊字符
    public static final String SPECIAL_CHARACTER = ".*((?=[\\x21-\\x7e]+)[^A-Za-z0-9]).*";
    // 数字
    public static final String NUMBER = ".*[\\d].*";
    // 字母
    public static final String WORD = ".*[a-zA-Z].*";

    public static boolean isMatch(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(str);
        return matcher.matches();
    }

    /**
     * 常用的表达式判断方法归总
     */
    // 匹配特殊字符
    public static boolean checkTSZF(String str) {
        return isMatch(str,SPECIAL_CHARACTER);
    }
    // 匹配数字
    public static boolean checkNumber(String str) {
        return isMatch(str,NUMBER);
    }
    // 匹配字母
    public static boolean checkWord(String str) {
        return isMatch(str,WORD);
    }
    // 判断是不是手机号
    public static boolean isPhoneNumber(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        if (str.length() != 11) {
            return false;
        }
        if (!str.startsWith("1")) {
            return false;
        }
        return true;
    }



}
