package com.template.coe.demo.controller

import org.junit.Assert;
import spock.lang.Specification;

class groovy extends Specification {

    def "default Spock Tutorials"() {
        given:
        String name1 = "kido"
        String name2 = "KIDO"

        when:
        name1 = name1.toLowerCase()
        name2 = name2.toLowerCase()

        then:
        name1 == name2
        Assert.assertEquals(name1, name2)
    }

//    Block Description
    def "block 설명을 추가"() {
        given: "이름을 설정한다."
        String name1 = "kido"
        String name2 = "KIDO"

        when: "소문자로 변경한다. "
        name1 = name1.toLowerCase()
        name2 = name2.toLowerCase()

        then: "결과를 비교한다."
        name1 == name2
        Assert.assertEquals(name1, name2)
    }

//    Expect 를 이용한 테스트 추가하기.
    def "Expect 를 활용하기"() {
        given: "이름을 설정한다."
        String name1 = "kido"
        String name2 = "KIDO"

        expect: "테스트를 수행하기 위해서 우선적으로 데이터를 검증하는 용도로 사용한다."
        name1.getClass().toString() == "class java.lang.String"
        name2.getClass().toString() == "class java.lang.String"
        name1.length() > 0

        when: "특정 액션이 발생했을때 해당 값을 반환한다."
        name1.length() >>> 100

        then:
        name1.length() == 100
    }

//    Exception 처리
    def "Exception 처리"() {
        given: "숫자값을 세팅한다."
        int op1 = 10
        int op2 = 0

        when: "100으로 나눈다. "
        def result = op1 / op2

        then: "예외를 처리한다."
        def e = thrown(ArithmeticException)
        println(e.message)

    }
}
