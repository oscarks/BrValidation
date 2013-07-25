import grails.plugins.brvalidation.constraint.CepConstraint
import grails.plugins.brvalidation.constraint.CnpjConstraint
import grails.plugins.brvalidation.constraint.CpfCnpjConstraint
import grails.plugins.brvalidation.constraint.CpfConstraint

import org.codehaus.groovy.grails.validation.ConstrainedProperty

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


/**
 * BrValidation Plugin. Implements gorm validation to same documents
 * (CPF and CNPJ) and codes formats (CEP) used in Brazil
 *
 * @author Oscar Konno Sampaio (oscarks@gmail.com)
 */
class BrValidationGrailsPlugin {
	def version = "0.3"
	def grailsVersion = "1.1.1 > *"
	def pluginExcludes = [
		'grails-app/domain/**'
	]

	def title = "Br Validation Plugin"
	def description = '''Implements gorm validation to same documents
(CPF and CNPJ) and codes formats (CEP) used in Brazil
'''

	def documentation = "http://grails.org/plugin/br-validation"

	def license = "APACHE"
	def organization = [ name: "Acception", url: "http://www.acception.com/" ]
	def developers = [ [ name: "Oscar Konno Sampaio", email: "oscarks@gmail.com" ]]
	def issueManagement = [ system: "GITHUB", url: "https://github.com/oscarks/BrValidation/issues" ]
	def scm = [ url: 'https://github.com/oscarks/BrValidation']

	def doWithSpring = {
		ConstrainedProperty.registerNewConstraint(CpfCnpjConstraint.CPF_CNPJ_CONSTRAINT, CpfCnpjConstraint)
		ConstrainedProperty.registerNewConstraint(CnpjConstraint.CNPJ_CONSTRAINT,CnpjConstraint)
		ConstrainedProperty.registerNewConstraint(CpfConstraint.CPF_CONSTRAINT,CpfConstraint)
		ConstrainedProperty.registerNewConstraint(CepConstraint.POSTAL_CODE_CONSTRAINT,CepConstraint)
	}
}
