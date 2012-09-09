/*
 * Copyright (c) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package grails.plugins.brvalidation.util

import org.springframework.context.ApplicationContext;
import org.codehaus.groovy.grails.web.context.ServletContextHolder;
import org.codehaus.groovy.grails.web.servlet.GrailsApplicationAttributes;

/**
  * Class with same util methods to access config variable.
  *
  * @author Oscar Konno Sampaio (oscarks@gmail.com)
  * @since 0.1
  */
class Util {
	public static ApplicationContext getApplicationContext() {
		return (ApplicationContext) ServletContextHolder.getServletContext().getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT);
	}
	static def getGrailsApplication() {
		getApplicationContext().getBean('grailsApplication');
	}
	
	static def getValidationFormat() {
		def vt=grailsApplication.config.grails.plugins.brValidation.validation.type
		if (vt && !vt.toLowerCase() in ['masked','unmasked','both']) 
			throw new RuntimeException("The type of validations defined in Config.groovy is invalid. Possibles values: 'masked', 'unmasked' and 'both'")
		// Set the default format (both) if none is defined
		if (!vt) vt='both'
		vt
	}	
}
