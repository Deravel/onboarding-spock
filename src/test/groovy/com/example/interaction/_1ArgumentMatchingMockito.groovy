package com.example.interaction

import com.example.Contrived
import com.example.Service
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatcher
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.*
import static org.mockito.AdditionalMatchers.*

@RunWith(MockitoJUnitRunner)
class _1ArgumentMatchingMockito {

	@Mock
	private Service service
    @InjectMocks
	private Contrived contrived

	@Test
	void shouldMatchArgumentEquals() {
		// given
		given(service.singleParamMethod("value")).willReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchArgumentNotEquals() {
		// given
		given(service.singleParamMethod(not(eq("value1")))).willReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value2")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchAnyArgument() {
		// given
		given(service.singleParamMethod(any())).willReturn("value-string")

		// when
		String value = contrived.singleParamDelegate(new Object())

		// then
		assert value == "value-string"
	}

	@Test
	void shouldMatchMultipleAnyArguments() {
		// given
		given(service.multiParamMethod(any(), any())).willReturn("onetwo")

		// when
		String value = contrived.multiParamDelegate(new Object(), new Object())

		// then
		assert value == "onetwo"
	}

	@Test
	void shouldMatchAnyArgumentOfType() {
		// given
		given(service.singleParamMethod(anyString())).willReturn("value")

		// when
		String value = contrived.singleParamDelegate("input-string")

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNullArgument() {
		// given
		given(service.singleParamMethod(isNull())).willReturn("value")

		// when
		String value = contrived.singleParamDelegate(null)

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchNotNullArgument() {
		// given
		given(service.singleParamMethod(isNotNull())).willReturn("value")

		// when
		String value = contrived.singleParamDelegate(new Object())

		// then
		assert value == "value"
	}

	@Test
	void shouldMatchDynamicPredicateWhereArgumentIsStringOfLengthGreaterThan4() {
		ArgumentMatcher matcher = {String argument -> argument.size() > 4} as ArgumentMatcher
		// given
		given(service.singleParamMethod(argThat(matcher))).willReturn("value")

		// when
		int randomNumber = new Random().nextInt(10000)
		String value = contrived.singleParamDelegate("1234" + randomNumber.toString())

		// then
		assert value == "value"
	}

}
