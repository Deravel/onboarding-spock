package com.example.basics

import com.example.Bean
import spock.lang.Specification

class _1BasicSpecification extends Specification {

	def "should use 'when' and 'then' to describe methods with side effects"() {
		given:
		List list = ['one']

		when:
		list.add('two')

		// verify list contents
		// SNIPPET START
		then:
		list == ['one', 'two']
		// SNIPPET END
	}

	def "should use 'expect' to describe purely functional methods"() {
		given:
		Bean bean1 = new Bean("correct")
		Bean bean2 = new Bean("correct")

		// verify bean equality
		// SNIPPET START
		expect:
		bean1.equals(bean2)
		// SNIPPET END
	}

	def "should use 'cleanup' to take care of any transient resources created by the individual test"() {
		given:
		File file = new File("some-file")

		when:
		file.write("file content")

		then:
		file.text == "file content"

		// delete the file as part of test cleanup
		// SNIPPET START
		cleanup:
		file.delete()
		// SNIPPET END
	}

	def "should use 'and' to break up blocks into logical pieces (very poor example, notwithstanding)"() {
		given:
		File file1 = new File('file-one')

		// SNIPPET START
		and:
		File file2 = new File('file-two')
		// SNIPPET END

		when:
		file1.write("file one content")

		and:
		file2.write("file two content")

		then:
		file1.text == "file one content"

		and:
		file2.text == "file two content"

		cleanup:
		file1.delete()

		and:
		file2.delete()
	}

}
