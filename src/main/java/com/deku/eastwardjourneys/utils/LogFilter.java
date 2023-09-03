package com.deku.eastwardjourneys.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class LogFilter extends AbstractFilter implements Filter {
    private Level minimumLevel = Level.DEBUG;

    /**
     * Sees of the record is loggable to the console.
     * Required override of log filter implementations for the Java logger.
     * Forge does not use this so we can default it to returning true regardless of the filter being applied.
     *
     * @param record The record we are trying to log
     * @return Whether we can log the given record
     */
    @Override
    public boolean isLoggable(LogRecord record) {
        return true;
    }

    /**
     * Override of the Log4j filter method.
     * Forge uses the Log4j logger so this will be called whenever an event is being logged.
     * It will not allow any logs below the minimum log level specified in this class to display on the console.
     *
     * @param event The event we are trying to log
     * @return Whether we can log the given event or must deny it
     */
    @Override
    public Result filter(LogEvent event) {
        if (minimumLevel.compareTo(event.getLevel()) < 0) {
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }

    /**
     * Sets the minimum log level
     *
     * @param level Level to set the logger to
     */
    public void setMinimumLevel(Level level) {
        minimumLevel = level;
    }
}
