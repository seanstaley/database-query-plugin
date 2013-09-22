package com.staleylabs.query;

import com.jivesoftware.base.plugin.Plugin;
import org.apache.log4j.Logger;

/**
 * Startup class for the Database Query Plugin.
 *
 * @author Sean M. Staley
 * @version 2.0
 * @since 1.0 (5/14/12)
 */
public class DatabaseQueryPlugin implements Plugin {

    private static final Logger log = Logger.getLogger(DatabaseQueryPlugin.class);

    public void initPlugin() {
        log.info("Starting Database Query Plugin....");
    }

    public void destroy() {
        log.info("Stopping Database Query Plugin....");
    }
}
