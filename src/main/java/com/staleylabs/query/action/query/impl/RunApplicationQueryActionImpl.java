package com.staleylabs.query.action.query.impl;

import com.staleylabs.query.action.query.RunQueryAction;
import com.staleylabs.query.service.QueryService;

/**
 * Controller layer Action for application layer queries.
 *
 * @author Sean M. Staley
 * @version 2.0 (10/6/13)
 */

public final class RunApplicationQueryActionImpl extends RunQueryAction {

    public void setApplicationQueryService(QueryService applicationQueryService) {
        queryService = applicationQueryService;
    }
}
