/**
 * Aibton.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.newsupplytech.jxsmh.until;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 打印日志工具类
 * @author huzhihui
 * @version v 0.1 2017/5/11 23:31 huzhihui Exp $$
 */
public class LoggerUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerUtils.class);

    /**
     * 打印INFO日志
     * @param LOGGER    LOGGER
     * @param formatStr formatStr    this is logger {0},{1},{2}
     * @param parameters    parameters
     */
    public static void info(Logger LOGGER, String formatStr, Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            String replaceStr = "{" + i + "}";
            formatStr = formatStr.replace(replaceStr, "%s");
        }
        formatStr = String.format(formatStr, parameters);
        LOGGER.info(formatStr);
    }

    /**
     * 打印WARN日志
     * @param LOGGER    LOGGER
     * @param formatStr formatStr   this is logger {0},{1},{2}
     * @param parameters    parameters
     */
    public static void warn(Logger LOGGER, String formatStr, Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            String replaceStr = "{" + i + "}";
            formatStr = formatStr.replace(replaceStr, "%s");
        }
        formatStr = String.format(formatStr, parameters);
        LOGGER.warn(formatStr);
    }

    /**
     * 打印ERROR日志
     * @param LOGGER    LOGGER
     * @param formatStr formatStr   this is logger {0},{1},{2}
     * @param parameters    parameters
     */
    public static void error(Logger LOGGER, String formatStr, Object... parameters) {
        for (int i = 0; i < parameters.length; i++) {
            String replaceStr = "{" + i + "}";
            formatStr = formatStr.replace(replaceStr, "%s");
        }
        formatStr = String.format(formatStr, parameters);
        LOGGER.error(formatStr);
    }
}
