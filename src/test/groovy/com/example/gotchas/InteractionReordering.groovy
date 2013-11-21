package com.example.gotchas

import com.example.Contrived
import com.example.Service
import spock.lang.Specification

class InteractionReordering extends Specification {

	private Service service = Mock()
	private Contrived contrived = new Contrived(service)

	def "interactions within a 'then' block are reordered at runtime and executed prior to the 'when' block "() {
		when: "the mock interaction is required during execution of the 'when' block"
		String value = contrived.singleParamDelegate("object")

		then: "any mock interactions in the 'then' block are reordered at runtime so it executes prior to the 'when' block"
		1 * service.singleParamMethod("object") >> "value"
		assert value == "value"


		when: "the mock interaction is not contained directly within the 'then' block"
		value = contrived.singleParamDelegate("object")

		then: "the 'interaction' method (which accepts a closure) is used to manually define one or more interactions"
		interaction {
			assertSingleParamMethodInvokedOnceAndReturns("value")
		}
		assert value == "value"

		then: "otherwise, the test will fail b/c the method is not reordered and the interactions are executed after the 'when' block"
		assertSingleParamMethodInvokedOnceAndReturns("value")
	}

	private void assertSingleParamMethodInvokedOnceAndReturns(String value) {
		1 * service.singleParamMethod("object") >> value
	}

	def "variables declared in a 'when' block are not available to interactions defined in a 'then' block due to reordering"() {
		when: "a variable is declared in the 'when' block"
		def available = "value"

		then: "it is available in the 'then' block"
		assert available == "value"


		when: "however, due to interaction recording, a variable declared in the 'when' block"
		def notAvailable = "value"

		then: "is not available when used by an interaction"
		service.singleParamMethod("object") >> notAvailable
	}

}
