package org.scooby.android;

import org.scooby.basic.Configuration;
import org.scooby.basic.LogRecord;

public interface LogCatConfiguration extends Configuration { 
    
    public default String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder
            .append("Process ").append(record.getProcessId()).append("; ")
            .append("Message ").append(record.getMessage()).append("; ");
        
        if (record.getData() != null) {
            builder.append("Payload ").append(record.getData()).append("; ");
        }
        
        return builder.toString();
    }
}
