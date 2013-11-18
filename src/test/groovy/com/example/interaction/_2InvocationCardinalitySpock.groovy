package com.example.interaction

import com.example.Contrived
import com.example.Service
import spock.lang.Specification

class _2InvocationCardinalitySpock extends Specification {
	
	private Service service = Mock()
	private Contrived contrived = new Contrived(service)

	/**
	 * The following syntax is used to verify cardinality of method invocations...
	 *     <number of required invocations> * <method invocation>
	 * If a return value is required, the right shift operator '>>' is used...
	 *     <number of required invocations> * <method invocation> >> <return value>
	 */
	def "should match exactly once"() {
		when:
		String value = contrived.singleParamDelegate("object")

		then:
		// SNIPPET START
		1 * service.singleParamMethod("object") >> "value"
		// SNIPPET END
		value == "value"
	}

	def "should match exactly twice"() {
		when:
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		then:
		// SNIPPET START
		2 * service.singleParamMethod("object") >> "value"
		// SNIPPET END
		values == ["value", "value"]
	}

	def "should not match any"() {
		when:
		List<String> values = contrived.singleParamDelegateForEach(new Object[0])

		then:
		// SNIPPET START
		0 * service.singleParamMethod("object")
        // SNIPPET END
        values == []
    }

	/**
	 * Groovy range syntax can be used to specify a cardinality range...
	 *     (<min number>..<max number>) * <method invocation>
	 */
	def "should match between once and twice"() {
		when:
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		then:
		// SNIPPET START
		(1..2) * service.singleParamMethod("object") >> "value"
		// SNIPPET END
		values == ["value", "value"]
	}

	/**
	 * The underscore character '_' is used within a range to indicate any value.
	 */
	def "should match twice or more"() {
		when:
		List<String> values = contrived.singleParamDelegateForEach("object", "object", "object")

		then:
		// SNIPPET START
		(2.._) * service.singleParamMethod("object") >> "value"
		// SNIPPET END
		values == ["value", "value", "value"]
	}

}
