package org.scooby.basic;

import java.util.Set;

public interface Configuration<Level extends LogLevel> {
    public Set<Level> levels();
    public boolean enabled(Level level);
}
