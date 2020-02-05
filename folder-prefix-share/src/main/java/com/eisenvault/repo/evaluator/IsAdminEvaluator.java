package com.eisenvault.repo.evaluator;

import org.alfresco.web.evaluator.BaseEvaluator;
import org.json.simple.JSONObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.connector.User;

/**
 * this evaluator is to check if a user is Admin.
 * Only admin will be able to see the action on which this evaluator is configured.
 */
public class IsAdminEvaluator extends BaseEvaluator {
	 
	@Override
	public boolean evaluate(JSONObject jsonObject) {
		RequestContext rc = ThreadLocalRequestContext.getRequestContext();
		User user = rc.getUser();
 
		return (user != null && user.isAdmin());
	}
}