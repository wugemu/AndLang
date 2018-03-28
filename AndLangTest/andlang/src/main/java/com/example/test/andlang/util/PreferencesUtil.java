package com.example.test.andlang.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * SharePreference工具类配置
 */
public class PreferencesUtil {
	public static SharedPreferences sharedPreferences = null;
	public static SharedPreferences.Editor editor = null;
	public static final String PREFERENCES_DEFAULT = "andlang";

	public PreferencesUtil(Context context, String file) {
		sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}
	/**
	 * Environment 获取sharedPreferences对象
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences getPreferences(Context context) {
		sharedPreferences = context.getSharedPreferences(PREFERENCES_DEFAULT,
				Context.MODE_PRIVATE);
		return sharedPreferences;
	}

	/**
	 * 获取editor对象
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences.Editor getEditor(Context context) {
		if (editor == null) {
			if (sharedPreferences == null) {
				sharedPreferences = getPreferences(context);
			}
			editor = sharedPreferences.edit();
		}

		return editor;
	}

	/**
	 * 设置int类型的数�?
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void putInt(Context context, String name,
                              int value) {
		if (editor == null) {
			editor = getEditor(context);
		}
		editor.putInt(name, value);
		editor.commit();
	}

	/**
	 * 设置String 类型的数�?
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void putString(Context context, String name,
                                 String value) {
		if (editor == null) {
			editor = getEditor(context);
		}
		editor.putString(name, value);
		editor.commit();
	}

	/**
	 * 设置boolean类型的数�?
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void putBoolean(Context context,
                                  String name, Boolean value) {
		if (editor == null) {
			editor = getEditor(context);
		}
		editor.putBoolean(name, value);
		editor.commit();
	}

	/**
	 * 设置Long 类型的数�?
	 * 
	 * @param context
	 * @param name
	 * @param value
	 */
	public static void putLong(Context context,  String name,
                               Long value) {
		if (editor == null) {
			editor = getEditor(context);
		}
		editor.putLong(name, value);
		editor.commit();
	}

	/**
	 * 设置float类型，默认返回�?0
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static void putFloat(Context context , String name,
                                Float value) {
		if (editor == null) {
			editor = getEditor(context);
		}
		editor.putFloat(name, value);
		editor.commit();
	}

	/**
	 * 获取int类型，默认返回�?-1
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static int getInt(Context context,  String name) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getInt(name, -1);
	}

	/**
	 * 获取String 类型，默认返回�?""
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getString(Context context,  String name) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getString(name, "");
	}
	/**
	 * 获取String 类型，默认返回�?""
	 *
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getString2(Context context,  String name, String defaultstr) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getString(name, defaultstr);
	}

	/**
	 * 获取boolean类型，默认返回�?false
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static boolean getBoolean(Context context,
                                     String name, boolean defaultvalue) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getBoolean(name, defaultvalue);
	}

	/**
	 * 获取Long类型，默认返回�?-1
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static Long getLong(Context context, String name) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getLong(name, -1);
	}

	/**
	 * 获取float类型，默认返回�?0
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static Float getFloat(Context context,  String name) {
		if (sharedPreferences == null) {
			sharedPreferences = getPreferences(context);
		}
		return sharedPreferences.getFloat(name, 0);
	}


	public static Map<String,Object> getAllData(){
		return (Map<String, Object>) sharedPreferences.getAll();
	}
}
