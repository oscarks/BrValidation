package grails.plugins.brvalidation.constraint

import grails.plugins.brvalidation.util.Util

import org.apache.commons.lang.StringUtils
import org.apache.commons.lang.math.NumberUtils
import org.codehaus.groovy.grails.validation.AbstractConstraint
import org.springframework.validation.Errors

class CpfCnpjConstraint extends AbstractConstraint {
		
	private static final CPF_REGEXP = /^([0-9]{3}\.){2}[0-9]{3}-[0-9]{2}$/
	private static final CNPJ_REGEXP = /^[0-9]{2}\.[0-9]{3}\.[0-9]{3}\/[0-9]{4}-[0-9]{2}$/
	
	public static final CPF_CNPJ_CONSTRAINT = "cpfcnpj";	
	public static final CPF_CONSTRAINT = "cpf";	
	public static final CNPJ_CONSTRAINT = "cnpj";
	

	public void setParameter(Object constraintParameter) {
		if(!(constraintParameter instanceof Boolean))
			throw new IllegalArgumentException("Parameter for constraint ["
			+CPF_CNPJ_CONSTRAINT+"] of property ["
			+constraintPropertyName+"] of class ["
			+constraintOwningClass+"] must be a boolean value");

		super.setParameter(constraintParameter);
	}
	
	private def isAllCharEquals(def str) {
		def c  = str[0] 
		for (def i in str) {
			if (c != i) return false				
		}		
		return true
	}

	protected void processValidate(Object target, Object propertyValue, Errors errors) {						
		def code
		def valid
		def str = propertyValue as String
		
		if (StringUtils.isNotEmpty(str)) {	
			if (isAllCharEquals(str))
				valid = false
			else {
				switch(verifyType()) {
					case "cpf":
						valid = verifyFormat(str, 11)
						code = CPF_CONSTRAINT
						break
					case "cnpj":
						valid = verifyFormat(str, 14)
						code = CNPJ_CONSTRAINT
						break
					default:
						valid = verifyFormat(str, 11) || verifyFormat(str, 14)
				}

				str = str.replaceAll("\\W", "")

				if (valid) {
					if (str.size() == 11)
						valid = validateCpf(str)
					else if (str.size() == 14)
						valid = validateCnpj(str)
				}
			}					
		}
		
		if (!valid) {
			if (!code) {
			   code = (str.size() <= 11) ? CPF_CONSTRAINT : CNPJ_CONSTRAINT 
			}
						
			def args = (Object[]) [constraintPropertyName, constraintOwningClass, propertyValue]
			rejectValue(target, errors, "default.not."+ code +".message", "not."+code, args)
		}
	}

	boolean supports(Class type) {
		return type != null && String.class.isAssignableFrom(type);
	}

	String getName() {
		return CPF_CNPJ_CONSTRAINT;
	}

	def validateCnpj( String cnpj ) {
		try{
			int soma = 0, aux, dig
			String calc = cnpj.substring(0,12)

			if ( cnpj.length() != 14 ) 
				return false
				
			char[] chr_cnpj = cnpj.toCharArray();
			
			for( int i = 0; i < 4; i++ ){
				if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 ) 
					soma += (chr_cnpj[i] - 48 ) * (6 - (i + 1)) 
			}
				
			for( int i = 0; i < 8; i++ ) {
				if ( chr_cnpj[i+4]-48 >=0 && chr_cnpj[i+4]-48 <=9 ) 
					soma += (chr_cnpj[i+4] - 48 ) * (10 - (i + 1)) 
			}
					
			dig = 11 - (soma % 11)
			calc += ( dig == 10 || dig == 11 ) ? "0" : dig as String
			soma = 0
			
			for ( int i = 0; i < 5; i++ ){
				if ( chr_cnpj[i]-48 >=0 && chr_cnpj[i]-48 <=9 ) 
					soma += (chr_cnpj[i] - 48 ) * (7 - (i + 1)) ;
			}
			
			for ( int i = 0; i < 8; i++ ){
				if ( chr_cnpj[i+5]-48 >=0 && chr_cnpj[i+5]-48 <=9 ) 
					soma += (chr_cnpj[i+5] - 48 ) * (10 - (i + 1))
			}
					
			dig = 11 - (soma % 11)
			calc += ( dig == 10 || dig == 11 ) ? "0" : dig as String
			
			return cnpj == calc
		}catch (Exception e){
			return false;
		}
	}

	def validateCpf(String cpf){

		try{
			boolean validado = true
			int d1, d2
			int dg1, dg2, resto
			int dgcpf			

			d1 = d2 = 0
			dg1 = dg2 = resto = 0			
							
			for (int i = 1; i < cpf.length() -1; i++) {
				dgcpf = cpf.substring(i -1, i) as Integer
				d1 = d1 + ( 11 - i ) * dgcpf
				d2 = d2 + ( 12 - i ) * dgcpf
			}
			
			resto = (d1 % 11);
			dg1 = (resto < 2) ? 0 : 11 - resto
			
			d2 += 2 * dg1;
			resto = (d2 % 11);
			dg2 = (resto < 2) ? 0 : 11 - resto
			
			def dgverif = cpf.substring(cpf.length()-2)
			def dgresult = (dg1 as String)  + (dg2 as String)
			
			return dgverif == dgresult
		}catch (Exception e){
			return false;
		}
	}
	
	def verifyType() {
		return "all"
	}
	
	def verifyFormat(value, Integer size) {
		def vt = Util.validationFormat		
		def mask = (size == 11) ? CPF_REGEXP : CNPJ_REGEXP		
										
		switch(vt) {
			case 'masked': return (value ==~ mask)
			case 'unmasked': return (NumberUtils.isNumber(value) && value.size() == size)
			case 'both': return  (value==~ mask) || (NumberUtils.isNumber(value) && value.size() == size)
		}
	}	
}
