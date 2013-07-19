package grails.plugins.brvalidation

class Person {

	String cpf
	String cnpj
	String cpfcnpj
	
    static constraints = {
		cpf ( nullable: true, cpf: true)
		cnpj ( nullable: true, cnpj: true)
		cpfcnpj (nullable: true, cpfcnpj: true)
    }
}
