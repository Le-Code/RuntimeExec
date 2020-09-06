package com.company;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * phone操作类
 */
public class PhoneOperate implements Operate {
    /**
     * 拷贝至phone
     * @param fromPth
     * @param toPth
     */
    @Override
    public List<String> copy(String fromPth, String toPth) {
        if (fromPth == null || toPth == null) {
            return null;
        }
        String command = "adb push " + fromPth + " " + toPth;
        System.out.println(command);
        List<String> resultList = CommandProcess.runShell(command);
        return resultList;
    }

    @Override
    public List<String> delete(String path) {
        if (path == null) {
            return null;
        }
        String command = "";
        if (path.endsWith("/")) {
            command = "adb shell rm -rf " + path;
        }else {
            command = "adb shell rm " + path;
        }
        List<String> callback = CommandProcess.runShell(command);
        return callback;
    }
}
