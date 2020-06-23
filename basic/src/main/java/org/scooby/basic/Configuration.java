package org.scooby.basic;

public interface Configuration {
    public boolean isEnabled(LogLevel level);
    public String formatRecord(LogRecord record);
}
