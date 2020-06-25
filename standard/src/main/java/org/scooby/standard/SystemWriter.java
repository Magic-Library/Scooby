package org.scooby.standard;

import static org.projector.utils.Nullable.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import org.scooby.basic.Configuration;
import org.scooby.basic.LogLevel;
import org.scooby.basic.LogRecord;
import org.scooby.basic.LogWriter;

public class SystemWriter implements LogWriter {
    public static final int SYSTEM_OUT = 0x2,
                            SYSTEM_ERR = 0x4;
    
    private Map<LogLevel, Integer> comparement;
    private Configuration configuration;

    @Override
    public void configurate(Configuration cfg) {
        checkNotNull(cfg, "Configuration");
        this.configuration = cfg;
        this.comparement = new HashMap<>();
    }

    @Override
    public void write(LogRecord record) {
        if (!configuration.isEnabled(record.getLevel())) {
            return;
        }
        
        String message = configuration.formatRecord(record);
        
        Integer flag = comparement.get(record.getLevel());
        if (flag == null) {
            flag = SYSTEM_OUT;
        }
        
        if ((flag & SYSTEM_OUT) == SYSTEM_OUT) {
            System.out.println(message);
        }
        if ((flag & SYSTEM_ERR) == SYSTEM_ERR) {
            System.err.println(message);
        }
    }
    
    public void set(LogLevel level, int flag) {
        checkNotNull(level, "Log level");
        if (flag != SYSTEM_OUT && flag != SYSTEM_ERR && flag != (SYSTEM_OUT | SYSTEM_ERR)) {
            throw new IllegalArgumentException("Incorrect flag: allowed SYSTEM_OUT, SYSTEM_ERR or (SYETEM_OUT | SYSTEM_ERR) for double-kill");
        }
        
        comparement.put(level, flag);
    }
}
