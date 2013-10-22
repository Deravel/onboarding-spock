package com.example

import spock.lang.Specification

class _3InvocationResponseSpock extends Specification {
	
	private ToStringService toStringService = Mock()
	private Contrived contrived = new Contrived(toStringService)

	/**
	 * The right shift operator '>>' is used to specify a return value
	 */
	def "should return single response"() {
		// SNIPPET START
		toStringService.toString("object") >> "value"
		// SNIPPET END

		when:
		String value = contrived.toString("object")

		then:
		value == "value"
	}

	/**
	 * The right triple shift operator '>>>' along with an array of values is used to chain multiple responses.
	 */
	def "should return chained responses"() {
		// SNIPPET START
		toStringService.toString("object") >>> ["value1", "value2"]
		// SNIPPET END

		when:
		List<String> values = contrived.toString("object", "object")

		then:
		values == ["value1", "value2"]
	}

	/**
	 * If a closure is provided as the response object, it's contents will be interpreted.
	 */
	def "should throw exception"() {
		// SNIPPET START
		toStringService.toString("object") >> { throw new RuntimeException() }
		// SNIPPET END

		when:
		contrived.toString("object")

		then:
		thrown(RuntimeException)
	}

	/**
	 * While the right triple shift operator '>>>' can be used to return multiple values, the values will simply be
	 * returned and not interpreted.  If one of the values needs to be interpreted, then the regular
	 * right shift operator '>>' can be chained.
	 */
	def "should return response on first invocation and throw exception on second"() {
		// SNIPPET START
		toStringService.toString("object") >> "value" >> { throw new RuntimeException() }
		// SNIPPET END

		when:
		String value = contrived.toString("object")

		then:
		value == "value"

		when:
		contrived.toString("object")

		then:
		thrown(RuntimeException)
	}

	/**
	 * As we learned previously, the underscore character '_' is used to signify any argument.  This can be used
	 * in combination with a closure as return value to selectively throw an exception.
	 * If the closure declareas a single untyped parameter, it is passed the input method arguments as a list.
	 */
	def "should throw exception if input is null"() {
		// SNIPPET START
		toStringService.toString(_) >> { args -> if (args[0] == null) throw new RuntimeException() }
		// SNIPPET END

		when:
		contrived.toString(null)

		then:
		thrown(RuntimeException)
	}

	/**
	 * If the closure declares a type parameter or parameters, the parameters will be passed to the closure as
	 * individual arguments.
	 */
	def "should return input argument appended with '-response'"() {
		// SNIPPET START
		toStringService.toString(_) >> { String arg -> "${arg}-response"}
		// SNIPPET END

		when:
		String value = contrived.toString("object")

		then:
		value == "object-response"
	}
	
}
