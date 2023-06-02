package com.skhynix.datahub.striimtqlparser2.batch;

public class ToTrailToJsonConfig {
    /**
     * 1. job
     * 2. step
     * 3. reader
     * 3.1 tql parser (open feign)
     * 4. writer
     */

    /**
     * tql parser
     * 1. export tql
     * 2.1. export tql parsing (return parsing dir)
     * 2.1. compare old / new files -> 달라진 files parsing (return parsing result)
     * 2.2. parsing files retrieve source/target tables -> striim cli (return retrieve data)
     * 2.3. replace new files to old files
     * 2.4. return parsing result (return parsing result)
     */
}
