package com.staleylabs.query.action;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import org.apache.log4j.Logger;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (3/17/13)
 */

public class AboutPluginAction extends AdminActionSupport {

    private static final Logger log = Logger.getLogger(AboutPluginAction.class);

    @Override
    public String execute() {
        log.debug("Entering About Page");

        return SUCCESS;
    }
}
