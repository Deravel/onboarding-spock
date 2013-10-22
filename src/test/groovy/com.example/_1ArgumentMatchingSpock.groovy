package com.example

import spock.lang.Specification

class _1ArgumentMatchingSpock extends Specification {

	private ToStringService toStringService = Mock()
	private Contrived contrived = new Contrived(toStringService)

	/**
	 * To set up an expection, simply invoke the method on the mock with appropriate arguments.
	 * The right shift operator '>>' is used to specify a return value
	 */
	def "should match arguments using equals"() {
		// SNIPPET START
		toStringService.toString("value") >> "value-string"
		// SNIPPET END

		when:
		String value = contrived.toString("value")

		then:
		value == "value-string"
	}

	/**
	 * The bang character '!' is used to match negation.
	 */
	def "should match argument not equals"() {
		// SNIPPET START
		toStringService.toString(!"value1") >> "value-string"
		// SNIPPET END

		when:
		String value = contrived.toString("value2")

		then:
		value == "value-string"
	}

	/**
	 * The underscore character '_' is used to match any argument.
	 */
	def "should match any argument"() {
		// SNIPPET START
		toStringService.toString(_) >> "value-string"
		// SNIPPET END

		when:
		String value = contrived.toString(new Object())

		then:
		value == "value-string"
	}

	/**
	 * The character sequence '*_' is used to match any number of arguments.
	 */
	def "should match multiple any arguments"() {
		// SNIPPET START
		toStringService.concatenate(*_) >> "onetwo"
		// SNIPPET END

		// when
		String value = contrived.concatenate(new Object(), new Object())

		// then
		value == "onetwo"
	}

	/**
	 * '_ as <type>' is used to match an argument of a specificed type
	 */
	def "should match any argument of type String"() {
		// SNIPPET START
		toStringService.toString(_ as String) >> "value"
		// SNIPPET END

		when:
		String value = contrived.toString("input-string")

		then:
		value == "value"
	}

	/**
	 * The null keyword is used to match a null argument.
	 */
	def "should match null argument"() {
		// SNIPPET START
		toStringService.toString(null) >> "value"
		// SNIPPET END

		when:
		String value = contrived.toString(null)

		then:
		value == "value"
	}

	/**
	 * The expression !null is used to match any non-null argument.
	 */
	def "should match not null argument"() {
		// SNIPPET START
		toStringService.toString(!null) >> "value"
		// SNIPPET END

		when:
		String value = contrived.toString(new Object())

		then:
		value == "value"
	}

	/**
	 * If a closure is used as an argument to an expectation, the expectation will match if the closure evaluates
	 * to true.
	 */
	def "should match dynamic predicate where argument is string of length greater than 4"() {
		// SNIPPET START
		toStringService.toString({String arg -> arg.size() > 4}) >> "value"
		// SNIPPET END

		when:
		int randomNumber = new Random().nextInt(10000)
		String value = contrived.toString("1234" + randomNumber.toString())

		then:
		value == "value"
	}

}
