package org.scooby.basic;

public enum LogLevel {
    DEBUG (0, "DEBUG"),
    INFO  (1, "INFO"),
    WARN  (2, "WARN"),
    ERROR (3, "ERROR"),
    FATAL (4, "FATAL");
    
    private int priority;
    private String name;
    
    private LogLevel(int priority, String name) {
        this.priority = priority;
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }    
}
