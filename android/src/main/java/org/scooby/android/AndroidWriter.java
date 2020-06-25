package org.scooby.android;

import static org.projector.utils.Nullable.checkNotNull;

import org.scooby.basic.Configuration;
import org.scooby.basic.LogRecord;
import org.scooby.basic.LogWriter;

public class AndroidWriter implements LogWriter {
    private Configuration configuration;
    private LogCat logCat;

    @Override
    public void configurate(Configuration cfg) {
        checkNotNull(cfg, "Configuration");
        this.configuration = cfg;
        this.logCat = LogCat.get();
    }

    @Override
    public void write(LogRecord record) {
        if (!configuration.isEnabled(record.getLevel())) {
            return;
        }
        
        String message = configuration.formatRecord(record);
        switch (record.getLevel()) {
            case DEBUG: logCat.d(record.getTag(), message, record.getError()); break;
            case ERROR: logCat.e(record.getTag(), message, record.getError()); break;
            case FATAL: logCat.wtf(record.getTag(), message, record.getError()); break;
            case INFO:  logCat.i(record.getTag(), message, record.getError()); break;
            case WARN:  logCat.w(record.getTag(), message, record.getError()); break;        
        }
    }
}
