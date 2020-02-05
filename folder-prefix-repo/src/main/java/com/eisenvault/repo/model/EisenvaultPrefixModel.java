package com.eisenvault.repo.model;

import org.alfresco.service.namespace.QName;

public class EisenvaultPrefixModel {
	
	//Namespace URI
	public static final String EV_PREFIX_CONTENT_MODEL_URI  = "http://prefix.eisenvault.net/model/content/1.0";
	
	//Aspect
	public static final QName ASPECT_FOLDER_PREFIX = QName.createQName(EV_PREFIX_CONTENT_MODEL_URI, "folderPrefix");
	
	//Property
	public static final QName PROP_PREFIX = QName.createQName(EV_PREFIX_CONTENT_MODEL_URI, "prefix");
}
