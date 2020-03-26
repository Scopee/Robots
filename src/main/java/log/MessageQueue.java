package log;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue extends LinkedBlockingQueue<LogEntry> {

    public MessageQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean add(LogEntry logEntry) {
        while (!offer(logEntry))
            poll();
        return true;
    }
}
