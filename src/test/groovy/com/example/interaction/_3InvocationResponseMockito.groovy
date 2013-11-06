package com.example.interaction

import com.example.Contrived
import com.example.Service
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.junit.Assert.fail
import static org.mockito.Mockito.when

@RunWith(MockitoJUnitRunner)
class _3InvocationResponseMockito {

	@Mock
	private Service service
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(service)
	}

	@Test
	void shouldReturnSingleResponse() {
		// given
		when(service.singleParamMethod("value")).thenReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldReturnChainedResponses() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value1").thenReturn("value2")

		// when
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		// then
		assert values == ["value1", "value2"]
	}

	@Test(expected = RuntimeException)
	void shouldThrowException() {
		// given
		when(service.singleParamMethod("object")).thenThrow(new RuntimeException())

		// when
		contrived.singleParamDelegate("object")
	}

	@Test
	void shouldReturnResponseOnFirstInvocationAndThrowExceptionOnSecond() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value").thenThrow(new RuntimeException())

		// when
		String value = contrived.singleParamDelegate("object")

		// then
		assert value == "value"

		// when
		try {
			contrived.singleParamDelegate("object")
			fail()
		} catch (RuntimeException ex) {}
	}

	@Test
	void shouldOptionallyThrowExceptionBasedOnInputArguments() {

	}

	@Test
	void shouldUseArgumentsToCalculateResponse() {

	}

}
