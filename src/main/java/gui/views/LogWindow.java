package gui.views;

import com.google.gson.JsonSerializer;
import gui.serialization.LogWindowSerializer;
import gui.serialization.SavableInternalFrame;
import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LogWindow extends SavableInternalFrame implements LogChangeListener {
    private LogWindowSource logSource;
    private TextArea logContent;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setBackground(Color.WHITE);
        logContent.setSize(500, 500);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();
    }

    public ArrayList<LogEntry> getMessages() {
        ArrayList<LogEntry> res = new ArrayList<>();
        for (LogEntry e : logSource.all()) {
            res.add(e);
        }
        return res;
    }

    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }

    @Override
    public void unregister() {
        logSource.unregisterListener(this);
    }

    @Override
    public JsonSerializer getSerializer() {
        return new LogWindowSerializer();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }
}
