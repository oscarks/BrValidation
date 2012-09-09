package grails.plugins.brvalidation.constraint

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import grails.plugins.brvalidation.constraint.CpfConstraint
/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class CpfConstraintTests {

    void setUp() {
        // Setup logic here
    }

    void tearDown() {
        // Tear down logic here
    }

    void testFormatingMasked() {
		config.grails.plugins.brValidation.validation.type='masked'
		def cpfv=new CpfConstraint()
		assertFalse(cpfv.validCpf(null,'25156698247'))
		assertTrue(cpfv.validCpf(null,'251.566.982-47'))
    }
}
