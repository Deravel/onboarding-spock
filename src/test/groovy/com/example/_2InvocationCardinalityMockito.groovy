package com.example

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import static org.mockito.Mockito.*


@RunWith(MockitoJUnitRunner)
class _2InvocationCardinalityMockito {

	@Mock
	private Service service
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(service)
	}

	@Test
	void shouldMatchExactlyOnce() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value")

		// when
		String value = contrived.singleParamDelegate("object")

		// then
		verify(service).singleParamMethod("object")
		assert value == "value"
	}

	@Test
	void shouldMatchExactlyTwice() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value")

		// when
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		// then
		verify(service, times(2)).singleParamMethod("object")
		assert values == ["value", "value"]
	}

	@Test
	void shouldNotMatchAny() {
		// when
		List<String> values = contrived.singleParamDelegateForEach(new Object[0])

		// then
		verify(service, never()).singleParamMethod("object")
		assert values == []
	}

	@Test
	void shouldMatchBetweenOnceAndTwice() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value")

		// when
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		// then
		verify(service, atLeast(1)).singleParamMethod("object")
		verify(service, atMost(2)).singleParamMethod("object")
		assert values == ["value", "value"]
	}

	@Test
	void shouldMatchTwiceOrMore() {
		// given
		when(service.singleParamMethod("object")).thenReturn("value")

		// when
		List<String> values = contrived.singleParamDelegateForEach("object", "object", "object")

		// then
		verify(service, atLeast(2)).singleParamMethod("object")
		assert values == ["value", "value", "value"]
	}

}
