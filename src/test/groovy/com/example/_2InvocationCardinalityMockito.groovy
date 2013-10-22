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
	private ToStringService toStringService
	private Contrived contrived

	@Before
	void setup() {
		contrived = new Contrived(toStringService)
	}

	@Test
	void shouldMatchExactlyOnce() {
		// given
		when(toStringService.toString("object")).thenReturn("value")

		// when
		String value = contrived.toString("object")

		// then
		verify(toStringService).toString("object")
		assert value == "value"
	}

	@Test
	void shouldMatchExactlyTwice() {
		// given
		when(toStringService.toString("object")).thenReturn("value")

		// when
		List<String> values = contrived.toString("object", "object")

		// then
		verify(toStringService, times(2)).toString("object")
		assert values == ["value", "value"]
	}

	@Test
	void shouldNotMatchAny() {
		// when
		List<String> values = contrived.toString(new Object[0])

		// then
		verify(toStringService, never()).toString("object")
		assert values == []
	}

	@Test
	void shouldMatchBetweenOnceAndTwice() {
		// given
		when(toStringService.toString("object")).thenReturn("value")

		// when
		List<String> values = contrived.toString("object", "object")

		// then
		verify(toStringService, atLeast(1)).toString("object")
		verify(toStringService, atMost(2)).toString("object")
		assert values == ["value", "value"]
	}

	@Test
	void shouldMatchTwiceOrMore() {
		// given
		when(toStringService.toString("object")).thenReturn("value")

		// when
		List<String> values = contrived.toString("object", "object", "object")

		// then
		verify(toStringService, atLeast(2)).toString("object")
		assert values == ["value", "value", "value"]
	}

}
