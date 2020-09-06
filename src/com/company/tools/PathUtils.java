package com.company.tools;

import com.company.CommandProcess;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.List;

public class PathUtils {

    public static String generatePath(TreePath path, String sep) {
        StringBuilder sb = new StringBuilder(sep);
        Object[] paths = path.getPath();
        for (int i = 1; i < paths.length; i++) {
            String pName = (String) ((DefaultMutableTreeNode)paths[i]).getUserObject();
            sb.append(pName+sep);
        }
        return sb.toString();
    }

    public static String generateWinPath(TreeNode[] path) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < path.length; i++) {
            String pName = path[i].toString();
            sb.append(pName + "\\");
        }
        if (!isWinDirectory(sb.toString())) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String generatePhonePath(TreeNode[] path) {
        StringBuilder sb = new StringBuilder("/");
        for (int i = 1; i < path.length; i++) {
            String pName = path[i].toString();
            sb.append(pName + "/");
        }
        if (!isPhoneDirectory(sb.toString())) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public static boolean isWinDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    public static boolean isPhoneDirectory(String path) {
        List<String> resultList = CommandProcess.runShell("adb shell ls " + path);
        if (resultList != null && resultList.size() > 0) {
            if (resultList.get(0).contains("Not a directory")) {
                return false;
            }
        }
        return true;
    }
}
