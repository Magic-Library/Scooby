package org.scooby.basic;

public interface LogWriter<Level extends LogLevel, Record extends LogRecord> {
    public void configurate(Configuration<Level> cfg);
    public void write(Record record);
}
