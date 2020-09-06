package com.company;

import java.util.List;

/**
 * window操作类
 */
public class WinOperate implements Operate {
    /**
     * 拷贝到win系统
     * @param fromPth
     * @param toPth
     */
    @Override
    public List<String> copy(String fromPth, String toPth) {
        String command = "adb pull " + fromPth + " " + toPth;
        System.out.println(command);
        List<String> callback = CommandProcess.runShell(command);
        return callback;
    }

    @Override
    public List<String> delete(String path) {
        return null;
    }
}
