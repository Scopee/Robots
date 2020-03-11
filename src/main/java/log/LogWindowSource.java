package log;

import java.util.ArrayList;
import java.util.Collections;

import java.util.concurrent.*;

/**
 * TODO:
 *  Этот класс порождает утечку ресурсов (связанные слушатели оказываются
*/
public class LogWindowSource {
    private int queueLength;

    private ConcurrentLinkedQueue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;
    private volatile LogChangeListener[] activeListeners;

    public LogWindowSource(int queueLength) {
        this.queueLength = queueLength;
        messages = new ConcurrentLinkedQueue<>();
        listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
            activeListeners = null;
        }
    }

    public void unregisterListener(LogChangeListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
            activeListeners = null;
        }
    }

    public void append(LogLevel logLevel, String strMessage) {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        if (messages.size() == queueLength)
            messages.poll();
        messages.add(entry);
        LogChangeListener[] activeListeners = this.activeListeners;
        if (activeListeners == null) {
            synchronized (listeners) {
                if (this.activeListeners == null) {
                    activeListeners = listeners.toArray(new LogChangeListener[0]);
                    this.activeListeners = activeListeners;
                }
            }
        }
        for (LogChangeListener listener : activeListeners) {
            listener.onLogChanged();
        }
    }

    public int size() {
        return messages.size();
    }

    public Iterable<LogEntry> range(int startFrom, int count) {
        if (startFrom < 0 || startFrom >= messages.size()) {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, messages.size());
        return subSequence(startFrom, indexTo);
    }

    public Iterable<LogEntry> all() {
        return messages;
    }

    private Iterable<LogEntry> subSequence(int startFrom, int indexTo) {
        ArrayList<LogEntry> entries = new ArrayList<>();
        int count = 0;
        for (LogEntry entry : messages) {
            if (count >= startFrom && count <= indexTo)
                entries.add(entry);
        }
        return entries;
    }
}
