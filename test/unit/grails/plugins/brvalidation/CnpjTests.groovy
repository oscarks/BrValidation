package grails.plugins.brvalidation

import grails.test.mixin.TestFor

@TestFor(Person)
class CnpjTests {
    
    void testCpfBoth() {		
		config.grails.plugins.brValidation.validation.type = "both"
		
		def person = new Person(cnpj: "789")
		assert !person.validate()		
		assert "not.cnpj" == person.errors["cnpj"].code
		
		person.cnpj = "84107697000195"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
		
		person.cnpj = "84107697000194"
		assert person.validate()
						
		person.cnpj = "84.107.697/0001-95"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
		
		person.cnpj = "84.107.697/0001-94"
		assert person.validate()		
		
		person.cnpj = "00779964969"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
						
		person.cnpj = "007.799.649-61"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
    }
	
	void testCpfMasked() {
		config.grails.plugins.brValidation.validation.type = "masked"
		
		def person = new Person(cnpj: "84107697000194")
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
		
		person.cnpj = "84.107.697/0001-94"
		assert person.validate()		
				
	}
	
	void testCpfUnmasked() {
		config.grails.plugins.brValidation.validation.type = "unmasked"
						
		def person = new Person(cnpj: "84.107.697/0001-94")
		assert !person.validate()
		assert "not.cnpj" == person.errors["cnpj"].code
		
		person.cnpj = "84107697000194"
		assert person.validate()			
	}
}
