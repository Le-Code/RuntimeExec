package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 命令处理类
 */
public class CommandProcess {

    /**
     * 运行shell脚本
     * @param shell
     */
    public static void execShell(String shell) {
        try {
            Runtime.getRuntime().exec(shell);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream runShellForIs(String shell) {
        try {
            Process process = Runtime.getRuntime().exec(shell);
            return process.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> runShell(String shell) {
        List<String> stringList = new ArrayList<>();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            InputStreamReader isr = new InputStreamReader(process.getInputStream(), "gbk");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() == 0) {
                    continue;
                }
                stringList.add(line);
            }
            return stringList;
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
