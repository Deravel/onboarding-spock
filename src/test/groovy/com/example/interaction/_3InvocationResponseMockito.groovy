package com.example.interaction

import com.example.Contrived
import com.example.Service
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalAnswers
import org.mockito.ArgumentMatcher
import org.mockito.Mock
import org.mockito.invocation.InvocationOnMock
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.stubbing.Answer

import static org.junit.Assert.fail
import static org.mockito.AdditionalAnswers.returnsFirstArg
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.any
import static org.mockito.Matchers.anyObject
import static org.mockito.Matchers.anyString
import static org.mockito.Matchers.argThat
import static org.mockito.Matchers.eq
import static org.mockito.Mockito.doAnswer
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
        //given
        when(service.singleParamMethod(anyObject())).thenAnswer(throwRuntimeExceptionIfNull())

        //when
        try {
            contrived.singleParamDelegate(null)
            fail()
        } catch (RuntimeException ex) {}
    }

    def Answer throwRuntimeExceptionIfNull() {
        new Answer<String>() {
            @Override
            String answer(InvocationOnMock invocation) throws Throwable {
                Object arg = invocation.getArguments()[0]
                if (arg == null) throw new RuntimeException()
            }
        }
    }

    @Test
	void shouldUseArgumentsToCalculateResponse() {
        //given
        when(service.singleParamMethod(anyString())).thenAnswer(useInputInReturn())

        //when
        String value = contrived.singleParamDelegate("object")

        //then
        assert value == "object-response"
    }

    def Answer useInputInReturn() {
        new Answer<String>() {
            @Override
            String answer(InvocationOnMock invocation) throws Throwable {
                return "${invocation.getArguments()[0]}-response" as String
            }
        }
    }

}
