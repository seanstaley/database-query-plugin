package com.jivesoftware.plugin.dbQuery;

import com.jivesoftware.base.plugin.Plugin;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 10:57 AM
 */
public class DatabaseQueryPlugin implements Plugin {
    Logger log = Logger.getLogger(DatabaseQueryPlugin.class);

    public void init() {
        log.info("Starting Database Query Plugin....");
    }

    public void destroy() {
        log.info("Stopping Database Query Plugin....");
    }
}
