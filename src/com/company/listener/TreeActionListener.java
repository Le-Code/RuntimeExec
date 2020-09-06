package com.company.listener;

import javax.security.auth.callback.Callback;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class TreeActionListener implements MouseListener, ActionListener, TreeSelectionListener {

    protected JTree tree;
    protected JPopupMenu menu;
    protected CallbackListener callbackListener;
    protected ExecutorService executor;

    public TreeActionListener(JTree tree) {
        this.tree = tree;
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    public void addMenu(String[] subItems){
        this.menu = new JPopupMenu();
        for (String itemName : subItems) {
            JMenuItem item = new JMenuItem(itemName);
            item.addActionListener(this);
            menu.add(item);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        tree.updateUI();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (menu != null) {
            TreePath path = tree.getPathForLocation(e.getX(), e.getY());
            if (path == null) { //没有任何选项被选中
                return;
            }
            tree.setSelectionPath(path);
            if (e.getButton() == 3) {
                menu.show(tree, e.getX(), e.getY());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        String action = menuItem.getText();
        actionPerform(action);
    }

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath path = e.getPath();
        onValueChanged(path);
    }

    protected abstract void onValueChanged(TreePath path);

    protected abstract void actionPerform(String action);
}
