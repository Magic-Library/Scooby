package org.scooby.android;

import java.lang.reflect.Method;
import static org.projector.utils.Nullable.ifNullOrNot;

public class LogCat {
    private static LogCat logCat;
    
    public static LogCat get() {
        return ifNullOrNot(logCat, () -> {
            try {
                return logCat = new LogCat(LogCat.class.getClassLoader());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }, i -> i);
    }
    
    private Method shortError, fullError;
    private Method shortWarn, fullWarn;
    private Method shortInfo, fullInfo;
    private Method shortDebug, fullDebug;
    private Method shortVerbose, fullVerbose;
    private Method shortWtf, fullWtf;
    
    public LogCat(ClassLoader loader) throws Exception {
        Class<?> logClass = loader.loadClass("android.util.Log");
        
        shortError = logClass.getDeclaredMethod("e", String.class, String.class);
        fullError = logClass.getDeclaredMethod("e", String.class, String.class, Throwable.class);
        
        shortWarn = logClass.getDeclaredMethod("w", String.class, String.class);
        fullWarn = logClass.getDeclaredMethod("w", String.class, String.class, Throwable.class);
        
        shortInfo = logClass.getDeclaredMethod("i", String.class, String.class);
        fullInfo = logClass.getDeclaredMethod("i", String.class, String.class, Throwable.class);
        
        shortDebug = logClass.getDeclaredMethod("d", String.class, String.class);
        fullDebug = logClass.getDeclaredMethod("d", String.class, String.class, Throwable.class);
        
        shortVerbose = logClass.getDeclaredMethod("v", String.class, String.class);
        fullVerbose = logClass.getDeclaredMethod("v", String.class, String.class, Throwable.class);   
        
        shortWtf = logClass.getDeclaredMethod("wtf", String.class, String.class);
        fullWtf = logClass.getDeclaredMethod("wtf", String.class, String.class, Throwable.class);    
        
    }
    
    public void e(String tag, String msg, Throwable tr) {
        invoke(shortError, fullError, tag, msg, tr);
    }
    
    public void w(String tag, String msg, Throwable tr) {
        invoke(shortWarn, fullWarn, tag, msg, tr);
    }
    
    public void i(String tag, String msg, Throwable tr) {
        invoke(shortInfo, fullInfo, tag, msg, tr);
    }
            
    public void d(String tag, String msg, Throwable tr) {
        invoke(shortDebug, fullDebug, tag, msg, tr);
    }
    
    public void v(String tag, String msg, Throwable tr) {
        invoke(shortVerbose, fullVerbose, tag, msg, tr);
    }
    
    public void wtf(String tag, String msg, Throwable tr) {
        invoke(shortWtf, fullWtf, tag, msg, tr);
    }
    
    private void invoke(Method shortMethod, Method fullMethod, String tag, String msg, Throwable tr) {
        try {
            if (tr == null) {
                shortMethod.invoke(null, tag, msg);
            } else {
                fullMethod.invoke(null, tag, msg, tr);
            }
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}   
