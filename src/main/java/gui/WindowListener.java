package gui;

import javax.swing.*;
import java.awt.event.*;

public class WindowListener implements java.awt.event.WindowListener {

    private JFrame pane;

    public WindowListener(JFrame pane) {
        this.pane = pane;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        if (FrameListener.dialogAnswer(pane) == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
