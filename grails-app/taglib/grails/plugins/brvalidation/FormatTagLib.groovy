package grails.plugins.brvalidation

import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException

class FormatTagLib {
	/**
	 * Format a CPF value with the mask 999.999.999-99
	 * 
	 * @attr cpf REQUIRED the cpf value
	 * @attr unknow character to be used when the CPF is not informed
	 */
	def formatCpf = { attrs->
		def cpf=attrs.cpf
		if (cpf) {
			if (cpf instanceof Number) {
			    def c=cpf.toString()
			    while (c.size()<11)
			        c='0'+c
			    cpf=c
			} 
			cpf=cpf.replaceAll('\\.','').replaceAll('-','')
			if (cpf.size()!=11) throw new GrailsTagException("CPF with invalid size (found ${cpf.size()} expected 11")
			out << "${cpf.substring(0,3)}.${cpf.substring(3,6)}.${cpf.substring(6,9)}-${cpf.substring(9,11)}"
		} else if (attrs.unknow) {
			if (!(attrs.unknow instanceof String) || attrs.unknow.size()!=1)
				throw new GrailsTagException("Unknow character invalid")
			def u=attrs.unknow
			out << u.multiply(3)+'.'+u.multiply(3)+'.'+u.multiply(3)+'-'+u.multiply(2)
		}
	}
	
	/**
	 * Format a CPF value with the mask 99.999.999/9999-99
	 *
	 * @attr cnpj REQUIRED the cnpj value
	 */
	def formatCnpj = { attrs->
		def cnpj=attrs.cnpj
		if (cnpj) {
			if (cnpj instanceof Number) {
			    def c=cnpj.toString()
			    while (c.size()<14)
			        c='0'+c
			    cnpj=c
			} 
			cnpj=cnpj.replaceAll('\\.','').replaceAll('-','').replaceAll('/','')
			if (cnpj.size()!=11) throw new GrailsTagException("CPF with invalid size (found ${cnpj.size()} expected 11")
			out << "${cnpj.substring(0,2)}.${cnpj.substring(2,5)}.${cnpj.substring(5,8)}/${cnpj.substring(8,12)}-${cnpj.substring(12,14)}"
		} else if (attrs.unknow) {
			if (!(attrs.unknow instanceof String) || attrs.unknow.size()!=1)
				throw new GrailsTagException("Unknow character invalid")
			def u=attrs.unknow
			out << u.multiply(2)+'.'+u.multiply(3)+'.'+u.multiply(3)+'/'+u.multiply(4)+'-'+u.multiply(2)
		}
	}
}
