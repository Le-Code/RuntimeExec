package com.company.listener;

import com.company.ProcessHandler;
import com.company.tools.PathUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.io.File;
import java.util.List;

public class WinTreeActionListener extends TreeActionListener {

    private final ProcessHandler processHandler;

    public WinTreeActionListener(JTree tree) {
        super(tree);
        processHandler = ProcessHandler.getInstance();
    }

    @Override
    protected void onValueChanged(TreePath path) {
        String winPath = PathUtils.generatePath(path, "\\");
        DefaultMutableTreeNode lastSelectedPathComponent =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        lastSelectedPathComponent.removeAllChildren();
        if (winPath.equals("\\")) { //表明是根目录
            File[] files = File.listRoots();
            for (File file : files) {
                lastSelectedPathComponent.add(new DefaultMutableTreeNode(file.getAbsolutePath()));
            }
        }else {
            File file = new File(winPath);
            if (file.isFile()) {
                return;
            }
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File f : files) {
                lastSelectedPathComponent.add(new DefaultMutableTreeNode(f.getName()));
            }
        }
    }

    @Override
    protected void actionPerform(String action) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        TreeNode[] path = node.getPath();
        String pathString = PathUtils.generateWinPath(path);
        switch (action) {
            case "copy":
                processHandler.setFromPath(pathString);
                break;
            case "paste":
                processHandler.setToPath(pathString);
                if (callbackListener != null) {
                    callbackListener.onStart("onStart : copy from phone path : " + processHandler.getFromPath() +
                            "to win path : " + processHandler.getToPath());
                }
                executor.execute(()->{
                    List<String> callback = processHandler.copyToWin();
                    if (callbackListener != null) {
                        callbackListener.onCopy(callback);
                    }
                });

                break;
        }
    }
}
