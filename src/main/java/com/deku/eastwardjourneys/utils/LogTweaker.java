package com.deku.eastwardjourneys.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.LoggerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogTweaker {
    private static LogFilter filter = new LogFilter();

    /**
     * Applies a filter to the logger so that only logs of the specified level or above are shown on the console
     *
     * @param level The minimum level to set as the filter
     */
    public static void applyLogFilterLevel(Level level) {
        filter.setMinimumLevel(level);

        List<LoggerConfig> foundLoggers = new ArrayList<>();

        LoggerContext logContext = (LoggerContext) LogManager.getContext(false);
        Map<String, LoggerConfig> configMap = logContext.getConfiguration().getLoggers();

        for (final LoggerConfig config : configMap.values()) {
            if (!foundLoggers.contains(config)) {
                config.addFilter(filter);
                foundLoggers.add(config);
            }
        }
    }
}
