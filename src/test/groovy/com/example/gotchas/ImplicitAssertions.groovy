package com.example.gotchas

import spock.lang.Specification

class ImplicitAssertions extends Specification {

	def "conditions in 'then' and 'expect' blocks do not require 'assert'"() {
		given:
		List items = []

		when:
		items.add("item")
		items.add("item")

		then:
		items[0] == "item"
		items[1] == "item"

		then:
		items[0] == "this is not the item"
		items[1] == "this is not the item"
	}

	def "conditions not directly in 'then' and 'expect' blocks do require 'assert'"() {
		given:
		List items = []

		when:
		items.add("item")
		items.add("item")

		then: "this condition is false but passes b/c the condition is defined within a closure"
		items.each { String item ->
			item == "this is not the item"
		}

		then: "this condition is false and correctly fails b/c the 'assert' key word is used"
		items.each { String item ->
			assert item == "this is not the item"
		}
	}

}
