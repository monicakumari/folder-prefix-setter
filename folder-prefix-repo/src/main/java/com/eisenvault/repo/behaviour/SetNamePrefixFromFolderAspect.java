package com.eisenvault.repo.behaviour;

import java.io.Serializable;
import java.util.Map;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.node.NodeServicePolicies.OnCreateNodePolicy;
import org.alfresco.repo.node.NodeServicePolicies.OnUpdatePropertiesPolicy;
import org.alfresco.repo.policy.Behaviour;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.QName;

import com.eisenvault.repo.model.EisenvaultPrefixModel;

/**
 * this behaviour will fetch prefix value of aspect "folder prefix" from the parent folder
 * and set this prefix value on document name on create inside this folder.
 */
public class SetNamePrefixFromFolderAspect implements OnCreateNodePolicy, OnUpdatePropertiesPolicy {
	
	private PolicyComponent policyComponent;
	private NodeService nodeService;
	private Behaviour onCreateNode, onUpdateProperties;
	
	public void init() {
		// Create behaviours
		this.onCreateNode = new JavaBehaviour(this, "onCreateNode", NotificationFrequency.TRANSACTION_COMMIT);
		this.onUpdateProperties = new JavaBehaviour(this, "onUpdateProperties", NotificationFrequency.TRANSACTION_COMMIT);
		//Bind behaviours
		this.policyComponent.bindClassBehaviour(OnCreateNodePolicy.QNAME, ContentModel.TYPE_CONTENT, this.onCreateNode);
		this.policyComponent.bindClassBehaviour(OnUpdatePropertiesPolicy.QNAME, ContentModel.TYPE_CONTENT, this.onUpdateProperties);
	}

	@Override
	public void onCreateNode(ChildAssociationRef childAssocRef) {	
		if(nodeService.exists(childAssocRef.getChildRef())) {
				setPrefixToName(childAssocRef.getParentRef(), childAssocRef.getChildRef(), 
						nodeService.getProperty(childAssocRef.getChildRef(), ContentModel.PROP_NAME).toString());
		}
	}
	
	@Override
	public void onUpdateProperties(NodeRef nodeRef, Map<QName, Serializable> before, Map<QName, Serializable> after) {		
		if(nodeService.exists(nodeRef)) {
			if(before.get(ContentModel.PROP_NAME) != null && after.get(ContentModel.PROP_NAME) != before.get(ContentModel.PROP_NAME)) {
				setPrefixToName(nodeService.getPrimaryParent(nodeRef).getParentRef(), nodeRef, after.get(ContentModel.PROP_NAME).toString());
			}
		}
	}
	
	public void setPrefixToName(NodeRef parentNodeRef, NodeRef nodeRef, String fileName) {
		
		if(nodeService.getType(parentNodeRef).equals(ContentModel.TYPE_FOLDER) &&
				nodeService.hasAspect(parentNodeRef, EisenvaultPrefixModel.ASPECT_FOLDER_PREFIX)) {
			
			String namePrefix = nodeService.getProperty(parentNodeRef, EisenvaultPrefixModel.PROP_PREFIX).toString();
			if(namePrefix != "" && namePrefix != null) {				
				nodeService.setProperty(nodeRef, ContentModel.PROP_NAME, namePrefix + "__" + fileName);
				//System.out.println("\n\n PROP_NAME :  " + nodeService.getProperty(nodeRef, ContentModel.PROP_NAME).toString());
			}
		}
	}
	
	public void setPolicyComponent(PolicyComponent policyComponent) {
		this.policyComponent = policyComponent;
	}
	
	public void setNodeService(NodeService nodeService) { 
		this.nodeService = nodeService; 
	}	
}