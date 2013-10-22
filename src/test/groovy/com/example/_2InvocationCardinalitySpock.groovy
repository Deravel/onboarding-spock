package com.example

import spock.lang.Specification

class _2InvocationCardinalitySpock extends Specification {
	
	private ToStringService toStringService = Mock()
	private Contrived contrived = new Contrived(toStringService)

	/**
	 * The following syntax is used to verify cardinality of method invocations...
	 *     <number of required invocations> * <method invocation>
	 * If a return value is required, the left shift operator '>>' is used...
	 *     <number of required invocations> * <method invocation> >> <return value>
	 */
	def "should match exactly once"() {
		when:
		String value = contrived.toString("object")

		then:
		// SNIPPET START
		1 * toStringService.toString("object") >> "value"
		// SNIPPET END
		value == "value"
	}

	def "should match exactly twice"() {
		when:
		List<String> values = contrived.toString("object", "object")

		then:
		// SNIPPET START
		2 * toStringService.toString("object") >> "value"
		// SNIPPET END
		values == ["value", "value"]
	}

	def "should not match any"() {
		when:
		List<String> values = contrived.toString(new Object[0])

		then:
		// SNIPPET START
		0 * toStringService.toString("object")
		// SNIPPET END
		values == []
	}

	/**
	 * Groovy range syntax can be used to specify a cardinality range...
	 *     (<min number>..<max number>) * <method invocation>
	 */
	def "should match between once and twice"() {
		when:
		List<String> values = contrived.toString("object", "object")

		then:
		// SNIPPET START
		(1..2) * toStringService.toString("object") >> "value"
		// SNIPPET END
		assert values == ["value", "value"]
	}

	/**
	 * The underscore character '_' is used within a range to indicate any value.
	 */
	def "should match twice or more"() {
		when:
		List<String> values = contrived.toString("object", "object", "object")

		then:
		// SNIPPET START
		(2.._) * toStringService.toString("object") >> "value"
		// SNIPPET END
		values == ["value", "value", "value"]
	}

	/**
	 * The underscore character '_' is used to specificy any number.
	 */
	def "should match any times including zero"() {
		when:
		String value = contrived.toString("object")

		then:
		// SNIPPET START
		_ * toStringService.toString("object") >> "value"
		// SNIPPET END
		assert value == "value"
	}

}
