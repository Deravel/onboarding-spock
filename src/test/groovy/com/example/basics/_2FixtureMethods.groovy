package com.example.basics

import spock.lang.Specification

class _2FixtureMethods extends Specification {

	def setupSpec() {
		println "setup specification"
	}

	def setup() {
		println "setup feature"
	}

	def cleanup() {
		println "cleanup feature"
	}

	def cleanupSpec() {
		println "cleanup specification"
	}

	def "should output fixture flow"() {
		println "test"

		expect:
		// there's nothing here to really fix, the purpose of this test is to simply demonstrate execution order
		// CLASS SNIPPET START
		true
		// CLASS SNIPPET END
	}

}
