package com.eisenvault.repo.action;

import java.util.List;

import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;

import com.eisenvault.repo.model.EisenvaultPrefixModel;

/**
 * this action will get prefix value from user input 
 * and set it to property "prefix" of aspect "folder prefix" 
 */
public class SetPrefixOnFolder extends ActionExecuterAbstractBase {
	
	private static final String PARAM_NAME_PREFIX = "name-prefix";	
	private NodeService nodeService;

	@Override
	protected void executeImpl(Action action, NodeRef nodeRef) {
		
		String prefix = (String) action.getParameterValue(PARAM_NAME_PREFIX);
		//System.out.println("\n\n -------------------\n noderef ::  " + nodeRef + "\n prefix ::  " + prefix);		
		nodeService.addAspect(nodeRef, EisenvaultPrefixModel.ASPECT_FOLDER_PREFIX, null);
		nodeService.setProperty(nodeRef, EisenvaultPrefixModel.PROP_PREFIX, prefix);
	}

	@Override
	protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
		paramList.add(new ParameterDefinitionImpl(PARAM_NAME_PREFIX, DataTypeDefinition.TEXT, false, getParamDisplayLabel(PARAM_NAME_PREFIX)));
	}

	public void setNodeService(NodeService nodeService) { 
		this.nodeService = nodeService; 
	}
}