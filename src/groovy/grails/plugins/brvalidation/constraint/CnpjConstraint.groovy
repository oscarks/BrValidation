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

package grails.plugins.brvalidation.constraint

import org.springframework.validation.Errors

/**
 * Add CPF validation to gorm constraints
 *
 * @author Oscar Konno Sampaio  (oscarks@gmail.com)
 * @since 0.1
 *
 * 12/12/2009 - version 0.1
 *		First commit
 * 28/08/2012 - version 0.2
 *		Added configuration of validation type (with and/ou without mask)
 */


public class CnpjConstraint extends CpfCnpjConstraint {
	
	def verifyType() {
		return CNPJ_CONSTRAINT
	}
	
	String getName() {
		return CNPJ_CONSTRAINT;
	}
}
