package com.staleylabs.query.action.query.impl;

import com.jivesoftware.community.analytics.AnalyticsManager;
import com.jivesoftware.community.entitlements.authorization.RequiresRole;
import com.staleylabs.query.action.query.RunQueryAction;
import com.staleylabs.query.service.QueryService;
import org.springframework.beans.factory.annotation.Required;

/**
 * Controller layer Action for analytic layer queries.
 *
 * @author Sean M. Staley
 * @version 2.0
 * @since 1.0 (5/15/12)
 */

@RequiresRole("Manage System")
public final class RunAnalyticsQueryActionImpl extends RunQueryAction {

    private AnalyticsManager analyticsManager;

    @Required
    public void setAnalyticsManager(AnalyticsManager analyticsManager) {
        this.analyticsManager = analyticsManager;
    }

    public boolean isAnalyticsAvailable(){
        return analyticsManager.isEnabled();
    }

   public void setAnalyticsQueryService(QueryService analyticsQueryService) {
        queryService = analyticsQueryService;
    }
}