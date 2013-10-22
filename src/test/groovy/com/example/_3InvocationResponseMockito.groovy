package com.example

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
	private ToStringService toStringService
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(toStringService)
	}

	@Test
	void shouldReturnSingleResponse() {
		// given
		when(toStringService.toString("value")).thenReturn("value-string")

		// when
		String value = contrived.toString("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldReturnChainedResponses() {
		// given
		when(toStringService.toString("object")).thenReturn("value1").thenReturn("value2")

		// when
		List<String> values = contrived.toString("object", "object")

		// then
		assert values == ["value1", "value2"]
	}

	@Test(expected = RuntimeException)
	void shouldThrowException() {
		// given
		when(toStringService.toString("object")).thenThrow(new RuntimeException())

		// when
		contrived.toString("object")
	}

	@Test
	void shouldReturnResponseOnFirstInvocationAndThrowExceptionOnSecond() {
		// given
		when(toStringService.toString("object")).thenReturn("value").thenThrow(new RuntimeException())

		// when
		String value = contrived.toString("object")

		// then
		assert value == "value"

		// when
		try {
			contrived.toString("object")
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
