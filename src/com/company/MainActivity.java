package com.company;

import com.company.listener.CallbackListener;
import com.company.listener.PhoneTreeActionListener;
import com.company.listener.TreeActionListener;
import com.company.listener.WinTreeActionListener;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class MainActivity implements CallbackListener {
    private JPanel panel1;
    private JTextField commandText;
    private JButton execCommandBtn;
    private JButton clearBtn;
    private JTree phoneTree;
    private JTree winTree;
    private JTextArea callbackArea;
    private JScrollPane callbackScrollPane;

    private TreeActionListener winTreeActionListener;
    private TreeActionListener phoneTreeActionListener;

    public MainActivity() {
        initView();
        initEvent();
    }

    private void initView() {
        callbackScrollPane.setPreferredSize(new Dimension(1000, 300));
    }

    private void initEvent() {
        execCommandBtn.addActionListener((e -> {
            execCommand();
        }));
        clearBtn.addActionListener((e -> {
            callbackArea.setText("");
        }));
    }

    /**
     * 进入adb shell 模式
     */
    private void toAdb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = CommandProcess.runShellForIs("adb remount");
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        callbackArea.append(newLine(line));
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 指定命令按钮
     */
    private void execCommand() {
        String commandString = commandText.getText();
        List<String> stringList = CommandProcess.runShell(commandString);
        for (String s: stringList) {
            callbackArea.append(newLine(s));
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainActivity");
        frame.setContentPane(new MainActivity().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public String newLine(String line) {
        return line + "\n";
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // initTree
        DefaultMutableTreeNode phoneRootNode = new DefaultMutableTreeNode("phone");
        phoneTree = new JTree(phoneRootNode);
        phoneTreeActionListener = new PhoneTreeActionListener(phoneTree);
        phoneTreeActionListener.setCallbackListener(this);
        phoneTree.addTreeSelectionListener(phoneTreeActionListener);

        DefaultMutableTreeNode winRootNode = new DefaultMutableTreeNode("win");
        winTree = new JTree(winRootNode);
        winTreeActionListener = new WinTreeActionListener(winTree);
        winTreeActionListener.setCallbackListener(this);
        winTree.addTreeSelectionListener(winTreeActionListener);

        //init menu
        initMenu();
    }

    private void initMenu() {
        winTreeActionListener.addMenu(new String[]{"copy", "paste"});
        winTree.addMouseListener(winTreeActionListener);

        phoneTreeActionListener.addMenu(new String[]{"copy", "paste", "delete"});
        phoneTree.addMouseListener(phoneTreeActionListener);
    }

    @Override
    public void onStart(String msg) {
        callbackArea.append(newLine(msg));
    }

    @Override
    public void onCopy(List<String> callback) {
        for (String c : callback) {
            callbackArea.append(newLine(c));
        }
    }

    @Override
    public void onDelete(List<String> callback) {
        if (callback == null) {
            callbackArea.append(newLine("delete error"));
        }
        if (callback.size() == 0) {
            callbackArea.append(newLine("delete success"));
        }
    }
}
