package com.example.interaction

import com.example.Contrived
import com.example.Service
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
	private Service service
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(service)
	}

	@Test
	void shouldMatchArgumentEquals() {
		// given
		when(service.singleParamMethod("value")).thenReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchArgumentNotEquals() {
		// given
		when(service.singleParamMethod(not(eq("value1")))).thenReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value2")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchAnyArgument() {
		// given
		when(service.singleParamMethod(any())).thenReturn("value-string")

		// when
		String value = contrived.singleParamDelegate(new Object())

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchMultipleAnyArguments() {
		// given
		when(service.multiParamMethod(any(), any())).thenReturn("onetwo")

		// when
		String value = contrived.multiParamDelegate(new Object(), new Object())

		// then
		assert value == "onetwo"
	}

	@Test
	void shouldMatchAnyArgumentOfType() {
		// given
		when(service.singleParamMethod(anyString())).thenReturn("value")

		// when
		String value = contrived.singleParamDelegate("input-string")

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNullArgument() {
		// given
		when(service.singleParamMethod(isNull())).thenReturn("value")

		// when
		String value = contrived.singleParamDelegate(null)

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNotNullArgument() {
		// given
		when(service.singleParamMethod(isNotNull())).thenReturn("value")

		// when
		String value = contrived.singleParamDelegate(new Object())

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchDynamicPredicateWhereArgumentIsStringOfLengthGreaterThan4() {
		ArgumentMatcher matcher = {String argument -> argument.size() > 4} as ArgumentMatcher
		// given
		when(service.singleParamMethod(argThat(matcher))).thenReturn("value")

		// when
		int randomNumber = new Random().nextInt(10000)
		String value = contrived.singleParamDelegate("1234" + randomNumber.toString())

		// then
		assert value == "value"
	}

}
