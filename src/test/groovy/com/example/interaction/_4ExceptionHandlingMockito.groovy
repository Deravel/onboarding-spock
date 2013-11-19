package com.example.interaction

import com.example.Contrived
import com.example.Service
import com.example.ServiceException
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.junit.Assert.fail

@RunWith(MockitoJUnitRunner)
class _4ExceptionHandlingMockito {

	@Mock
	private Service service
	@InjectMocks
	private Contrived contrived
	@Rule
	public ExpectedException exception = ExpectedException.none()

	@Test(expected = ServiceException)
	void shouldDetectExceptionByType() {
		// when
		contrived.throwException("failure!", 1)
	}

	@Test
	void shouldDetectExceptionByTypeAndAssertInternalValues_theOldWay() {
		// given
		String expectedMessage = "failure!"
		int expectedStatusCode = 1

		try {
			// when
			contrived.throwException(expectedMessage, expectedStatusCode)
			fail() // gotta remember the fail() here!
		} catch (ServiceException ex) {
			// then
			assert ex.message == expectedMessage
			assert ex.statusCode == expectedStatusCode
		}
	}

	@Test
	void shouldDetectExceptionByTypeAndAssertInternalValues_theNewHotness() {
		// given
		String expectedMessage = "failure!"
		int expectedStatusCode = 1
		exception.expect(ServiceException)
		// NOTE: the following line should work but fails with a NoSuchMethodError; likely, it's a
		// mismatch between spock and junit hamcrest libraries
//		exception.expectMessage(expectedMessage)
		exception.expect(StatusCodeMatcher.hasStatusCode(expectedStatusCode))

		// when
		contrived.throwException(expectedMessage, expectedStatusCode)
	}

	public static class StatusCodeMatcher extends TypeSafeMatcher<ServiceException> {

		public static StatusCodeMatcher hasStatusCode(int statusCode) {
			return new StatusCodeMatcher(statusCode);
		}

		private int actualStatusCode;
		private int expectedStatusCode;

		private StatusCodeMatcher(int expectedStatusCode) {
			this.expectedStatusCode = expectedStatusCode;
		}

		@Override
		public  boolean matchesSafely(ServiceException exception) {
			this.actualStatusCode = exception.getStatusCode()
			return actualStatusCode == expectedStatusCode
		}

		@Override
		public void describeTo(Description description) {
			description.appendValue(actualStatusCode).appendText(" was not found, expecting ").appendValue(expectedStatusCode);
		}

	}

}
