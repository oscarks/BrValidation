package grails.plugins.brvalidation

import grails.plugins.*
import grails.plugins.brvalidation.constraint.CepConstraint
import grails.plugins.brvalidation.constraint.CnpjConstraint
import grails.plugins.brvalidation.constraint.CpfCnpjConstraint
import grails.plugins.brvalidation.constraint.CpfConstraint
import grails.validation.ConstrainedProperty

class BrValidationGrailsPlugin extends Plugin {

    def grailsVersion = "3.0.1 > *"
    def dependsOn = [:]

    def pluginExcludes = [
        "grails-app/domain/**",
        "grails-app/views/error.gsp"
    ]

    def title = "Br Validation Plugin" // Headline display name of the plugin
    def author = "Oscar Konno Sampaio"
    def authorEmail = "oscarks@gmail.com"
    def description = '''Implements gorm validation to same documents
(CPF and CNPJ) and codes formats (CEP) used in Brazil
'''
    def profiles = ['web']

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/br-validation"

    def license = "APACHE"

    def issueManagement = [system: "github", url: "https://github.com/oscarks/BrValidation/issues"]
    def scm = [ url: "https://github.com/oscarks/BrValidation" ]
    def developers = [
            [ name: "Oscar Konno", email: "oscarks@gmail.com" ],
            [ name: "Leandro G. Gehlen", email: "leandrogehlen@gmail.com" ]
    ]

    Closure doWithSpring() { {->
            ConstrainedProperty.registerNewConstraint(CpfCnpjConstraint.CPF_CNPJ_CONSTRAINT, CpfCnpjConstraint.class)
            ConstrainedProperty.registerNewConstraint(CnpjConstraint.CNPJ_CONSTRAINT,CnpjConstraint.class)
            ConstrainedProperty.registerNewConstraint(CpfConstraint.CPF_CONSTRAINT,CpfConstraint.class)
            ConstrainedProperty.registerNewConstraint(CepConstraint.POSTAL_CODE_CONSTRAINT,CepConstraint.class)
        }
    }

    void doWithDynamicMethods() {
        // TODO Implement registering dynamic methods to classes (optional)
    }

    void doWithApplicationContext() {
        // TODO Implement post initialization spring config (optional)
    }

    void onChange(Map<String, Object> event) {
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    void onConfigChange(Map<String, Object> event) {
        // TODO Implement code that is executed when the project configuration changes.
        // The event is the same as for 'onChange'.
    }

    void onShutdown(Map<String, Object> event) {
        // TODO Implement code that is executed when the application shuts down (optional)
    }
}
