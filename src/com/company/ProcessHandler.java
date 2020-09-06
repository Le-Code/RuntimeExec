package com.company;

import java.util.List;

public class ProcessHandler {

    private String fromPath;
    private String toPath;
    private final Operate winOperate;
    private final Operate phoneOperate;

    private ProcessHandler(){
        winOperate = new WinOperate();
        phoneOperate = new PhoneOperate();
    }

    private static ProcessHandler instance;

    public static ProcessHandler getInstance(){
        if (instance == null) {
            instance = new ProcessHandler();
        }
        return instance;
    }

    public void setFromPath(String fromPath) {
        this.fromPath = fromPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }

    public String getFromPath() {
        return fromPath;
    }

    public String getToPath() {
        return toPath;
    }

    public List<String> copyToPhone() {
        return phoneOperate.copy(fromPath, toPath);
    }

    public List<String> copyToWin() {
        if (fromPath == null || toPath == null) {
            return null;
        }
        return winOperate.copy(fromPath, toPath);
    }

    public List<String> deletePhone(String path) {
        if (path == null) {
            return null;
        }
        return phoneOperate.delete(path);
    }
}
