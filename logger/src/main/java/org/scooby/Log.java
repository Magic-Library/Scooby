package org.scooby;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.scooby.basic.LogLevel;
import org.scooby.basic.LogRecord;
import org.scooby.basic.LogWriter;
import static org.projector.utils.Nullable.ifNullOrNot;

public class Log {    
    @SuppressWarnings("rawtypes")
    private static Map<Class, Log> instances;
    
    static {
        instances = new HashMap<>();
    }

    static Log forClass(Class<?> clazz) {
        return ifNullOrNot(instances.get(clazz), () -> {
            return new Log(clazz);
        }, l -> l);
    }
    
    private LogWriter writer;
    
    public Log(Class<?> clazz) {
        instances.put(clazz, this);
    }
    
    public void setWriter(LogWriter writer) {
        this.writer = writer;
    }
    
    public void debug(String message) {
        write(LogLevel.DEBUG, null, message, null, null, stackTrace());
    }
    
    public void debug(String message, Object data) {
        write(LogLevel.DEBUG, null, message, data, null, stackTrace());
    }
    
    public void debug(String message, Object data, Throwable error) {
        write(LogLevel.DEBUG, null, message, data, error, stackTrace());
    }
    
    public void debug(String tag, String message, Object data) {
        write(LogLevel.DEBUG, tag, message, data, null, stackTrace());
    }
    
    public void debug(String tag, String message, Object data, Throwable error) {
        write(LogLevel.DEBUG, tag, message, data, error, stackTrace());
    }
    
    public void info(String message) {
        write(LogLevel.INFO, null, message, null, null, stackTrace());
    }
    
    public void info(String message, Object data) {
        write(LogLevel.INFO, null, message, data, null, stackTrace());
    }
    
    public void info(String message, Object data, Throwable error) {
        write(LogLevel.INFO, null, message, data, error, stackTrace());
    }
    
    public void info(String tag, String message, Object data) {
        write(LogLevel.INFO, tag, message, data, null, stackTrace());
    }
    
    public void info(String tag, String message, Object data, Throwable error) {
        write(LogLevel.INFO, tag, message, data, error, stackTrace());
    }
    
    public void warn(String message) {
        write(LogLevel.WARN, null, message, null, null, stackTrace());
    }
    
    public void warn(String message, Object data) {
        write(LogLevel.WARN, null, message, data, null, stackTrace());
    }
    
    public void warn(String message, Object data, Throwable error) {
        write(LogLevel.WARN, null, message, data, error, stackTrace());
    }
    
    public void warn(String tag, String message, Object data) {
        write(LogLevel.WARN, tag, message, data, null, stackTrace());
    }
    
    public void warn(String tag, String message, Object data, Throwable error) {
        write(LogLevel.WARN, tag, message, data, error, stackTrace());
    }
    
    public void error(String message) {
        write(LogLevel.ERROR, null, message, null, null, stackTrace());
    }
    
    public void error(String message, Object data) {
        write(LogLevel.ERROR, null, message, data, null, stackTrace());
    }
    
    public void error(String message, Object data, Throwable error) {
        write(LogLevel.ERROR, null, message, data, error, stackTrace());
    }
    
    public void error(String tag, String message, Object data) {
        write(LogLevel.ERROR, tag, message, data, null, stackTrace());
    }
    
    public void error(String tag, String message, Object data, Throwable error) {
        write(LogLevel.ERROR, tag, message, data, error, stackTrace());
    }
    
    public void fatal(String message) {
        write(LogLevel.FATAL, null, message, null, null, stackTrace());
    }
    
    public void fatal(String message, Object data) {
        write(LogLevel.FATAL, null, message, data, null, stackTrace());
    }
    
    public void fatal(String message, Object data, Throwable error) {
        write(LogLevel.FATAL, null, message, data, error, stackTrace());
    }
    
    public void fatal(String tag, String message, Object data) {
        write(LogLevel.FATAL, tag, message, data, null, stackTrace());
    }
    
    public void fatal(String tag, String message, Object data, Throwable error) {
        write(LogLevel.FATAL, tag, message, data, error, stackTrace());
    }
    
    private StackTraceElement[] stackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace == null || stackTrace.length < 2) {
            return new StackTraceElement[0];
        }
        
        StackTraceElement[] result = new StackTraceElement[stackTrace.length - 1];
        System.arraycopy(stackTrace, 0, result, 0, stackTrace.length - 1);
        return result;
    }
    
    private String pid() {
        RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
        String jvmName = bean.getName();
        return jvmName.split("@")[0];
    }
    
    private void write(LogLevel level, String tag, String message, Object data, Throwable error, StackTraceElement[] stackTrace) {
        if (tag == null) {
            if (stackTrace != null && stackTrace.length > 1) {
                tag = stackTrace[stackTrace.length - 2].getClassName();
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
            .setStackTrace(stackTrace)
            .build();
        writer.write(record);
    }
}
