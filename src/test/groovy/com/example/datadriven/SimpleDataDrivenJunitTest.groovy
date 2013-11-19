package com.example.datadriven

import com.example.Contrived
import com.example.Service
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

import static org.mockito.BDDMockito.given
import static org.mockito.Mockito.mock

@RunWith(Parameterized)
class SimpleDataDrivenJunitTest {

	@Parameterized.Parameters(name = "{index}: singleParamDelegate({0}) = {1}")
	public static Collection<Object[]> inputAndReturnValues() {
		return Arrays.asList(
				["input1", "return1"].toArray(),
				["input2", "return2"].toArray()
		)
	}

	private Service service
	private Contrived contrived
	private String inputParameter
	private String expectedReturnValue

	public SimpleDataDrivenJunitTest(String inputParameter, String expectedReturnValue) {
		this.inputParameter = inputParameter
		this.expectedReturnValue = expectedReturnValue
	}

	@Before
	void setup() {
		service = mock(Service)
		contrived = new Contrived(service)
	}

	@Test
	public void simpleDataDrivenTest() {
		// given
		given(service.singleParamMethod(inputParameter)).willReturn(expectedReturnValue)

		// when
		String actualReturnValue = contrived.singleParamDelegate(inputParameter)

		// then
		assert actualReturnValue == expectedReturnValue
	}

}
