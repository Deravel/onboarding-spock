package com.example.interaction

import com.example.Contrived
import com.example.Service
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.invocation.InvocationOnMock
import org.mockito.runners.MockitoJUnitRunner
import org.mockito.stubbing.Answer

import static org.junit.Assert.fail
import static org.mockito.BDDMockito.given
import static org.mockito.Matchers.anyObject
import static org.mockito.Matchers.anyString

@RunWith(MockitoJUnitRunner)
class _3InvocationResponseMockito {

	@Mock
	private Service service
    @InjectMocks
	private Contrived contrived

	@Test
	void shouldReturnSingleResponse() {
		// given
		given(service.singleParamMethod("value")).willReturn("value-string")

		// when
		String value = contrived.singleParamDelegate("value")

		// then
		assert value == "value-string"
	}

	@Test
	void shouldReturnChainedResponses() {
		// willReturn
		given(service.singleParamMethod("object")).willReturn("value1").willReturn("value2")

		// when
		List<String> values = contrived.singleParamDelegateForEach("object", "object")

		// then
		assert values == ["value1", "value2"]
	}

	@Test(expected = RuntimeException)
	void shouldThrowException() {
		// given
		given(service.singleParamMethod("object")).willThrow(new RuntimeException())

		// when
		contrived.singleParamDelegate("object")
	}

	@Test
	void shouldReturnResponseOnFirstInvocationAndThrowExceptionOnSecond() {
		// given
		given(service.singleParamMethod("object")).willReturn("value").willThrow(new RuntimeException())

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
        given(service.singleParamMethod(anyObject())).willAnswer(throwRuntimeExceptionIfNull())

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
        given(service.singleParamMethod(anyString())).willAnswer(useInputInReturn())

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
