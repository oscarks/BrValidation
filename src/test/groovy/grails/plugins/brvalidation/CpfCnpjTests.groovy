package grails.plugins.brvalidation

import grails.plugins.brvalidation.constraint.CpfCnpjConstraint
import grails.test.mixin.TestFor

import org.codehaus.groovy.grails.validation.ConstrainedProperty
import org.junit.BeforeClass

@TestFor(Person)
class CpfCnpjTests {

	@BeforeClass
	static void initConstraint() {
		ConstrainedProperty.registerNewConstraint(CpfCnpjConstraint.CPF_CNPJ_CONSTRAINT, CpfCnpjConstraint)
	}

	void testCpfCnpjBoth() {
		config.grails.plugins.brValidation.validation.type = "both"

		def person = new Person(cpfcnpj: "789")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpfcnpj"].code

		person.cpfcnpj = "00779964961"
		assert !person.validate()

		person.cpfcnpj = "00779964969"
		assert person.validate()

		person.cpfcnpj = "84107697000194"
		assert person.validate()

		person.cpfcnpj = "94107697000194"
		assert !person.validate()

		person.cpfcnpj = "007.799.649-61"
		assert !person.validate()

		person.cpfcnpj = "007.799.649-69"
		assert person.validate()

		person.cpfcnpj = "007-799.649.69"
		assert !person.validate()

		person.cpfcnpj = "84.107.697/0001-94"
		assert person.validate()

		person.cpfcnpj = "94.107.697/0001-94"
		assert !person.validate()

		person.cpfcnpj = "94.107.697\\0001-94"
		assert !person.validate()

		person.cpfcnpj = "11111111111111"
		assert !person.validate()

		person.cpfcnpj = "22222222222"
		assert !person.validate()
	}

	void testCpfCnpjMasked() {
		config.grails.plugins.brValidation.validation.type = "masked"

		def person = new Person(cpfcnpj: "00779964969")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpfcnpj"].code

		person.cpfcnpj = "007.799.649-69"
		assert person.validate()

		person.cpfcnpj = "84107697000194"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cpfcnpj"].code

		person.cpfcnpj = "84.107.697/0001-94"
		assert person.validate()
	}

	void testCpfCnpjUnmasked() {
		config.grails.plugins.brValidation.validation.type = "unmasked"

		def person = new Person(cpfcnpj: "007.799.649-69")
		assert !person.validate()
		assert "not.cpf" == person.errors["cpfcnpj"].code

		person.cpfcnpj = "00779964969"
		assert person.validate()

		person.cpfcnpj = "84.107.697/0001-94"
		assert !person.validate()
		assert "not.cnpj" == person.errors["cpfcnpj"].code

		person.cpfcnpj = "84107697000194"
		assert person.validate()
	}
}
