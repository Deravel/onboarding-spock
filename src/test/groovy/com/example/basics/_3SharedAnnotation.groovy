package com.example.basics

import com.example.Bean
import spock.lang.Shared
import spock.lang.Specification

class _3SharedAnnotation extends Specification {

	/**
	 * Any resource which is used from setupSpec or cleanupSpec must either be annotated with @Shared or declared static.
	 * Shared variables will be initialized once for the entire specification.
 	 */
	// CLASS SNIPPET START
	@Shared
	// CLASS SNIPPET END
	private Bean sharedBean

	def setupSpec() {
		sharedBean = new Bean("sharedValue")
	}

	def cleanupSpec() {
		sharedBean = null
	}

	def "test"() {
		expect:
		sharedBean != null
	}

}
