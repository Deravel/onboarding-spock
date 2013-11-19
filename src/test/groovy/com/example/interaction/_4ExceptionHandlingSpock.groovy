package com.example.interaction

import com.example.Contrived
import com.example.Service
import com.example.ServiceException
import spock.lang.Specification

class _4ExceptionHandlingSpock extends Specification {

	private Service service = Mock()
	private Contrived contrived = new Contrived(service)

	/**
	 * The 'thrown' method is used to indicate an expected exception.  The method
	 * accepts one parameter - the type of the expected exception.
	 */
	def "should detect exception by type"() {
		when:
		contrived.throwException("failure!", 1)

		then:
		// SNIPPET START
		thrown(ServiceException)
		// SNIPPET END
	}

	/**
	 * The 'thrown' method returns the thrown exception which can then be inspected
	 * and used to make assertions.
	 */
	def "should detect exception by type and assert internal values"() {
		given:
		String expectedMessage = "failure!"
		int expectedStatusCode = 1

		when:
		contrived.throwException(expectedMessage, expectedStatusCode)

		then:
		// SNIPPET START
		ServiceException ex = thrown(ServiceException)
		ex.message == expectedMessage
		ex.statusCode == expectedStatusCode
		// SNIPPET END
	}

}
