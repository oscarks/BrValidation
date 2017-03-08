Br Validation
=============

Implements same validations in grails constraints for units used in application from Brazil: CPF, CNPJ and CEP.
CPF - Cadastro de Pessoa Física (like a Security Social Numeber in USA)
CNPJ - Cadastro Nacional de Pessoa Juridica
CEP - Postal Code to Brazilian territory

Install
-------

In command line:

```
grails install-plugin br-validation
```

or add the dependency to BuildConfig:

```groovy
dependencies {
	// specify dependencies here under either 'build', 'compile', 'runtime', 'test' or 'provided' scopes eg.
	runtime 'org.grails.plugins:br-validation:0.3'
}
```

Usage
-----

In domain class add constaints to CEP, CPF and CNPJ fields. Ex.:

```groovy
class PessoaFisica {
	String nome
	Date dataNascimento
	String cpf
	static constraints {
		cpf cpf:true
	}
}
```

```groovy
class PessoaJuridica {
	String nome
	String razaoSocial
	String cnpj
	static constraints {
		cnpj cnpj:true
	}
}

```

```groovy
class Pessoa {
	String nome
	String cpfCnpj
	static constraints {
		cpfCnpj cpfcnpj: true
	}
}

```

Configuration
-------------

The CPF and CNPJ validated by BrValidation can have two formats: with and without mark characters (mask):

With Mask:
	CPF : 671.473.226-06
	CNPJ: 74.053.840/0001-59
Without Mask:
	CPF : 67147322606
	CNPJ: 74053840000159

The way that will be used to validate is configured in Config.groovy. Add the following entry:
```
grails.plugins.brValidation.validation.type=[masked|unmasked|both]
```

then, if you define:
```
grails.plugins.brValidation.validation.type=masked
```

the BrValidation will consider a valid CPF or CNPJ when they has mask. In the same way if unmasked is configured only unmasked data will pass in validation.
If both is configured, boths, masked and unmasked data is validated.


Tag Lib
-------

Now the BrValidation has a tag to format unmasked data to render masked one. For this use the tag bellow in gsp files:

```html
<g:formatCpf value="${personInstance.cpf"/>

<g:formatCnpj value="${personInstance.cnpj"/>

<g:formatCpfCnpj value="${personInstance.cpfcnpj"/>
```
