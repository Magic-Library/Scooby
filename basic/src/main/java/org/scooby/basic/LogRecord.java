package org.scooby.basic;

import static org.projector.utils.Nullable.checkNotNull;

public class LogRecord {
    private Integer processId;
    private LogLevel level;
    private String message;
    private Object data;
    private Throwable error;
    private StackTraceElement[] stackTrace;
    
    private LogRecord() {}

    public int getProcessId() {
        return processId;
    }

    public LogLevel getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }
    
    public boolean isValid() {
        return processId != null && level != null && (message != null || data != null || error != null) && stackTrace != null && stackTrace.length > 0;
    }
    
    public class Builder {
        private LogRecord record;
        
        public Builder() {
            record = new LogRecord();
        }
        
        public Builder setProcessId(int processId) {
            record.processId = processId;
            return this;
        }

        public Builder setLevel(LogLevel level) {
            checkNotNull(level, "Log level", null);
            record.level = level;
            return this;
        }

        public Builder setMessage(String message) {
            record.message = message;
            return this;
        }

        public Builder setData(Object data) {
            record.data = data;
            return this;
        }

        public Builder setError(Throwable error) {
            record.error = error;
            return this;
        }

        public Builder setStackTrace(StackTraceElement[] stackTrace) {
            checkNotNull(level, "Stack trace elements", null);
            if (stackTrace.length == 0) {
                throw new IllegalArgumentException("Stack trace must have at least one item");
            }
            
            record.stackTrace = stackTrace;
            return this;
        }
        
        public LogRecord build() {
            if (!record.isValid()) {
                throw new IllegalStateException("Not all required properties applied");
            }
            
            return record;
        }
    }
}
