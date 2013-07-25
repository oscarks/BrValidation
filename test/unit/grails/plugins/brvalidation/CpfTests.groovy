package grails.plugins.brvalidation

import grails.plugins.brvalidation.constraint.CpfConstraint
import grails.test.mixin.TestFor

import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.junit.BeforeClass

@TestFor(Person)
class CpfTests {

	@BeforeClass
	static void initConstraint() {
		ConstrainedProperty.registerNewConstraint(CpfConstraint.CPF_CONSTRAINT, CpfConstraint)
	}

	void testCpfBoth() {
		config.grails.plugins.brValidation.validation.type = "both"

		def person = new Person(cpf: "789")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "00779964961"
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "00779964969"
		assert person.validate()

		person.cpf = "007.799.649-61"
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "007.799.649-69"
		assert person.validate()

		person.cpf = "84.107.697/0001-94"
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "84107697000194"
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code
	}

	void testCpfMasked() {
		config.grails.plugins.brValidation.validation.type = "masked"

		def person = new Person(cpf: "00779964969")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "007.799.649-69"
		assert person.validate()
	}

	void testCpfUnmasked() {
		config.grails.plugins.brValidation.validation.type = "unmasked"

		def person = new Person(cpf: "007.799.649-69")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpf"].code

		person.cpf = "00779964969"
		assert person.validate()
	}
}
