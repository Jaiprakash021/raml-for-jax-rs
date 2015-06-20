/*
 * Copyright 2013-2015 (c) MuleSoft, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package org.raml.jaxrs.codegen.core.ext;

import java.lang.annotation.Annotation;
import java.util.Collection;

import org.raml.jaxrs.codegen.core.Names;
import org.raml.model.Action;
import org.raml.model.MimeType;
import org.raml.model.Raml;
import org.raml.model.Resource;
import org.raml.model.parameter.AbstractParam;

import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;

public class ResourceDisplayNameBasedMethodNameBuilder implements
		MethodNameBuilderExtension {

	@Override
	public void onCreateResourceInterface(JDefinedClass resourceInterface,
			Resource resource) {}

	@Override
	public void onAddResourceMethod(JMethod method, Action action,
			MimeType bodyMimeType, Collection<MimeType> uniqueResponseMimeTypes) {}

	@Override
	public boolean AddParameterFilter(String name, AbstractParam parameter,
			Class<? extends Annotation> annotationClass, JMethod method) {
		return false;
	}

	@Override
	public void setRaml(Raml raml) {}

	@Override
	public String buildResourceMethodName(Action action, MimeType bodyMimeType,Resource resource) {
		if(action==null||resource==null){
			return null;
		}
		String displayName = resource.getDisplayName();
		if(displayName==null||displayName.trim().isEmpty()){
			return null;
		}
		displayName = displayName.trim();
		displayName = displayName.substring(0,1).toUpperCase() + displayName.substring(1);
		String type = action.getType().toString().toLowerCase();
		
		String result = type + displayName + Names.buildMimeTypeInfix(bodyMimeType);
		return result;
	}

}