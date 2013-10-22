package com.example

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.mockito.Matchers.*
import static org.mockito.AdditionalMatchers.*
import static org.mockito.Mockito.when


@RunWith(MockitoJUnitRunner)
class _1ArgumentMatchingMockito {

	@Mock
	private ToStringService toStringService
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(toStringService)
	}

	@Test
	void shouldMatchArgumentEquals() {
		// given
		when(toStringService.toString("value")).thenReturn("value-string")

		// when
		String value = contrived.toString("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchArgumentNotEquals() {
		// given
		when(toStringService.toString(not(eq("value1")))).thenReturn("value-string")

		// when
		String value = contrived.toString("value2")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchAnyArgument() {
		// given
		when(toStringService.toString(any())).thenReturn("value-string")

		// when
		String value = contrived.toString(new Object())

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchMultipleAnyArguments() {
		// given
		when(toStringService.concatenate(any(), any())).thenReturn("onetwo")

		// when
		String value = contrived.concatenate(new Object(), new Object())

		// then
		assert value == "onetwo"
	}

	@Test
	void shouldMatchAnyArgumentOfType() {
		// given
		when(toStringService.toString(anyString())).thenReturn("value")

		// when
		String value = contrived.toString("input-string")

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNullArgument() {
		// given
		when(toStringService.toString(isNull())).thenReturn("value")

		// when
		String value = contrived.toString(null)

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNotNullArgument() {
		// given
		when(toStringService.toString(isNotNull())).thenReturn("value")

		// when
		String value = contrived.toString(new Object())

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchDynamicPredicateWhereArgumentIsStringOfLengthGreaterThan4() {
		ArgumentMatcher matcher = {String argument -> argument.size() > 4} as ArgumentMatcher
		// given
		when(toStringService.toString(argThat(matcher))).thenReturn("value")

		// when
		int randomNumber = new Random().nextInt(10000)
		String value = contrived.toString("1234" + randomNumber.toString())

		// then
		assert value == "value"
	}

}
