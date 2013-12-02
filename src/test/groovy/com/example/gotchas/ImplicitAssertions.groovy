package com.example.gotchas

import spock.lang.Specification

class ImplicitAssertions extends Specification {

	def "conditions directly under 'then' and 'expect' blocks do not require 'assert'"() {
		when:
		String item = "item"

		then: "the assertion is performed and the condition passes"
		item == "item"

		and: "the assertion is performed and the condition fails"
		item == "not the item"
	}

	def "conditions not directly in 'then' and 'expect' blocks do require 'assert'"() {
		given:
		List items = []

		when:
		items << "item1"
		items << "item2"

		then: "this condition evaluates to false but the test incorrectly passes b/c the condition is defined within a closure"
		items.each { String item ->
			item == "not the item"
		}

		and: "this condition evaluates to false and the test correctly fails b/c the 'assert' key word is used"
		items.each { String item ->
			assert item == "not the item"
		}
	}

}
