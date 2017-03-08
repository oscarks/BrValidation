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

import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

/**
* Add CEP validation to gorm constraints
*
* @author Oscar Konno Sampaio (oscarks@gmail.com)
* @since 0.1
*
* 12/12/2009 - version 0.1
*		First commit
*/

public class CepConstraint extends AbstractConstraint {

    private static final String DEFAULT_NOT_POSTAL_CODE_MESSAGE_CODE = "default.not.cep.message";
    public static final String POSTAL_CODE_CONSTRAINT = "cep";

    private boolean postalCode;

    public void setParameter(Object constraintParameter) {
        if(!(constraintParameter instanceof Boolean))
        throw new IllegalArgumentException("Parameter for constraint ["
        +POSTAL_CODE_CONSTRAINT+"] of property ["
        +constraintPropertyName+"] of class ["
        +constraintOwningClass+"] must be a boolean value");

        this.postalCode = ((Boolean)constraintParameter).booleanValue();
        super.setParameter(constraintParameter);
    }

    protected void processValidate(Object target, Object propertyValue, Errors errors) {
        if (! validPostalCode(target, propertyValue)) {
            def args = (Object[]) [constraintPropertyName, constraintOwningClass,
            propertyValue]
            super.rejectValue(target, errors, DEFAULT_NOT_POSTAL_CODE_MESSAGE_CODE,
                "not." + POSTAL_CODE_CONSTRAINT, args);
            }
        }

        boolean supports(Class type) {
            return type != null && String.class.isAssignableFrom(type);
        }

        String getName() {
            return POSTAL_CODE_CONSTRAINT;
        }

        boolean validPostalCode(target, propertyValue) {
            propertyValue==~ /^[0-9]{5}-[0-9]{3}$/
        }

    }
