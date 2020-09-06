package com.company;

import com.company.tools.PathUtils;

import java.util.List;

public class AdbProcess {

    private static final String PREFFIX = "adb shell ls ";

    public static List<String> findRootPath() {
        List<String> rootPath = CommandProcess.runShell(PREFFIX);
        return rootPath;
    }

    public static List<String> findNextPath(String preFold) {
        if (!PathUtils.isPhoneDirectory(preFold)) { //是目录
            return null;
        }
        List<String> nextPath = CommandProcess.runShell(PREFFIX + preFold);
        return nextPath;
    }

}
