package org.scooby;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.scooby.basic.LogLevel;
import org.scooby.basic.LogRecord;
import org.scooby.basic.LogWriter;

public class Log {    
    @SuppressWarnings("rawtypes")
    private static Map<Class, Log> instances;
    
    static {
        instances = new HashMap<>();
    }

    static Log forClass(Class<?> clazz) {
        return instances.get(clazz);
    }
    
    private LogWriter writer;
    
    public Log(Class<?> clazz) {
        instances.put(clazz, this);
    }
    
    public void setWriter(LogWriter writer) {
        this.writer = writer;
    }
    
    public void debug(String message) {
        debug(message, null);
    }
    
    public void debug(String message, Object data) {
        debug(message, data, null);
    }
    
    public void debug(String message, Object data, Throwable error) {
        debug(null, message, data, error);
    }
    
    public void debug(String tag, String message, Object data) {
        debug(tag, message, data, null);
    }
    
    public void debug(String tag, String message, Object data, Throwable error) {
        write(LogLevel.DEBUG, tag, message, data, error);
    }
    
    public void info(String message) {
        info(message, null);
    }
    
    public void info(String message, Object data) {
        info(message, data, null);
    }
    
    public void info(String message, Object data, Throwable error) {
        info(null, message, data, error);
    }
    
    public void info(String tag, String message, Object data) {
        info(tag, message, data, null);
    }
    
    public void info(String tag, String message, Object data, Throwable error) {
        write(LogLevel.INFO, tag, message, data, error);
    }
    
    public void warn(String message) {
        warn(message, null);
    }
    
    public void warn(String message, Object data) {
        warn(message, data, null);
    }
    
    public void warn(String message, Object data, Throwable error) {
        warn(null, message, data, error);
    }
    
    public void warn(String tag, String message, Object data) {
        warn(tag, message, data, null);
    }
    
    public void warn(String tag, String message, Object data, Throwable error) {
        write(LogLevel.WARN, tag, message, data, error);
    }
    
    public void error(String message, Object data) {
        error(message, data, null);
    }
    
    public void error(String message, Object data, Throwable error) {
        error(null, message, data, error);
    }
    
    public void error(String tag, String message, Object data) {
        error(tag, message, data, null);
    }
    
    public void error(String tag, String message, Object data, Throwable error) {
        write(LogLevel.ERROR, tag, message, data, error);
    }
    
    public void fatal(String message, Object data) {
        fatal(message, data, null);
    }
    
    public void fatal(String message, Object data, Throwable error) {
        fatal(null, message, data, error);
    }
    
    public void fatal(String tag, String message, Object data) {
        fatal(tag, message, data, null);
    }
    
    public void fatal(String tag, String message, Object data, Throwable error) {
        write(LogLevel.FATAL, tag, message, data, error);
    }
    
    private void write(LogLevel level, String tag, String message, Object data, Throwable error) {
        if (tag == null) {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                tag = stackTrace[stackTrace.length - 1].getClassName();
            }
        } 
        
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
