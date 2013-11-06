package com.example.datadriven

import com.example.Contrived
import com.example.Service
import spock.lang.Specification
import spock.lang.Unroll

class DataDrivenSpock extends Specification {

	private Service toStringService = Mock()
	private Contrived contrived = new Contrived(toStringService)

	def "simple test with data tables"() {
		when:
		String actualReturnValue = contrived.singleParamDelegate(inputParameter)

		then:
		1 * toStringService.singleParamMethod(inputParameter) >> expectedReturnValue
		assert actualReturnValue == expectedReturnValue

		where:
		inputParameter | expectedReturnValue
		"input1"       | "return1"
		"input2"       | "return2"
	}

	def "simple test using data pipes"() {
		when:
		String actualReturnValue = contrived.singleParamDelegate(inputParameter)

		then:
		1 * toStringService.singleParamMethod(inputParameter) >> expectedReturnValue
		assert actualReturnValue == expectedReturnValue

		where:
		inputParameter << ["input1", "input2"]
		expectedReturnValue << ["return1", "return2"]
	}

	def "simple test with variable assignment"() {
		when:
		String actualReturnValue = contrived.singleParamDelegate(inputParameter)

		then:
		1 * toStringService.singleParamMethod(inputParameter) >> expectedReturnValue
		assert actualReturnValue == expectedReturnValue

		where:
		inputParameter | _
		"input1"       | _
		"input2"       | _
		expectedReturnValue = "return-${inputParameter}"
	}

	@Unroll
	def "simple test with unrolling and placeholders should return #expectedReturnValue when input is #inputParameter"() {
		when:
		String actualReturnValue = contrived.singleParamDelegate(inputParameter)

		then:
		1 * toStringService.singleParamMethod(inputParameter) >> expectedReturnValue
		assert actualReturnValue == expectedReturnValue

		where:
		inputParameter | expectedReturnValue
		"input1"       | "return1"
		"input2"       | "return2"
	}

}
