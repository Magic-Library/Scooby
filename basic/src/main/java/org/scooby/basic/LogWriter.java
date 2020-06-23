package org.scooby.basic;

public interface LogWriter {
    public void configurate(Configuration cfg);
    public void write(LogRecord record);
}
