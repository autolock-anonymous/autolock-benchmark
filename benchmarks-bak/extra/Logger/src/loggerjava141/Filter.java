/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */


package loggerjava141;

import loggerjava141.LogRecord;

/**
 * A Filter can be used to provide fine grain control over
 * what is logged, beyond the control provided by log levels.
 * <p>
 * Each Logger and each Handler can have a filter associated with it.
 * The Logger or Handler will call the isLoggable method to check
 * if a given LogRecord should be published.  If isLoggable returns
 * false, the LogRecord will be discarded.
 *
 * @version %I%, %G%
 * @since 1.4
 */

public interface Filter {

    /**
     * Check if a given log record should be published.
     * @param record  a LogRecord
     * @return true if the log record should be published.
     */
    public boolean isLoggable(LogRecord record);

}
