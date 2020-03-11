package gui;

import log.LogChangeListener;

import javax.swing.*;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import java.awt.*;

public class FrameListener implements InternalFrameListener {
    private JInternalFrame frame;

    public FrameListener(JInternalFrame frame) {
        this.frame = frame;
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
        if (dialogAnswer(frame) == JOptionPane.YES_OPTION){
            frame.dispose();
        }
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameIconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeiconified(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {

    }

    public static int dialogAnswer(Component component){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        return JOptionPane.showConfirmDialog(component,
                "Вы действительно хотите закрыть окно?",
                "Выход",
                dialogButton);
    }
}
