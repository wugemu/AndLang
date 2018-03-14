package com.example.test.andlang.andlangutil;

/**
 * Created by root on 18-3-13.
 */

public interface LangHttpInterface<T> {
    void success(T busModel,String tag);
    void empty(String tag);
    void error(String tag);
}
