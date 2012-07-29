package com.jivesoftware.plugin.dbQuery.action.audit;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.audit.QueryAuditor;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 11:02 PM
 */
public class QueryAuditorAction extends AdminActionSupport {

	private static final long serialVersionUID = -281176533728456888L;
	
	QueryAuditor queryAuditor;

    @Override
    public String execute(){
        return SUCCESS;
    }
}
