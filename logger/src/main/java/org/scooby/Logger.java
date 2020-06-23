package org.scooby;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.scooby.basic.LogLevel;
import org.scooby.basic.LogRecord;
import org.scooby.basic.LogWriter;

public class Logger {    
    @SuppressWarnings("rawtypes")
    private static Map<Class, Logger> instances;
    
    static {
        instances = new HashMap<>();
    }

    static Logger forClass(Class<?> clazz) {
        return instances.get(clazz);
    }
    
    private LogWriter writer;
    
    public Logger(Class<?> clazz) {
        instances.put(clazz, this);
    }
    
    public void setWriter(LogWriter writer) {
        this.writer = writer;
    }
    
    public void debug(String tag, String message, Object data) {
        write(LogLevel.DEBUG, tag, message, data, null);
    }
    
    private void write(LogLevel level, String tag, String message, Object data, Throwable error) {
        LogRecord record = new LogRecord.Builder()
            .setLevel(level)
            .setTimestamp(new Date(System.currentTimeMillis()))
            .setTag(tag)
            .setMessage(message)
            .setData(data)
            .setError(error)
            .setProcessId(pid())
            .build();
        writer.write(record);
    }
    
    private String pid() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String jvmName = bean.getName();
        return jvmName.split("@")[0];
    }
}
