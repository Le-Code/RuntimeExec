package com.company.listener;

import com.company.AdbProcess;
import com.company.ProcessHandler;
import com.company.tools.PathUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.List;

public class PhoneTreeActionListener extends TreeActionListener {

    private final ProcessHandler processHandler;

    public PhoneTreeActionListener(JTree tree) {
        super(tree);
        processHandler = ProcessHandler.getInstance();
    }

    @Override
    protected void onValueChanged(TreePath path) {
        String phonePath = PathUtils.generatePath(path, "/");
        List<String> nextPath = AdbProcess.findNextPath(phonePath);
        DefaultMutableTreeNode lastSelectedPathComponent =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        lastSelectedPathComponent.removeAllChildren();
        if (lastSelectedPathComponent == null) {
            return;
        }
        if (nextPath == null) {
            return;
        }
        for (String p : nextPath) {
            lastSelectedPathComponent.add(new DefaultMutableTreeNode(p));
        }
    }

    @Override
    protected void actionPerform(String action) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        TreeNode[] path = node.getPath();
        String pathString = PathUtils.generatePhonePath(path);
        switch (action) {
            case "copy":
                processHandler.setFromPath(pathString);
                break;
            case "paste":
                processHandler.setToPath(pathString);
                if (callbackListener != null) {
                    callbackListener.onStart("onStart : copy from win path : " + processHandler.getFromPath() +
                            "to phone path : " + processHandler.getToPath());
                }
                executor.execute(()->{
                    List<String> callback = processHandler.copyToPhone();
                    if (callbackListener != null) {
                        callbackListener.onCopy(callback);
                    }
                });
                break;
            case "delete":
                if (callbackListener != null) {
                    callbackListener.onStart("onStart : delete file : " + pathString);
                }
                executor.execute(()->{
                    List<String> callback = processHandler.deletePhone(pathString);
                    if (callbackListener != null) {
                        callbackListener.onDelete(callback);
                    }
                });
                break;
        }
    }
}
