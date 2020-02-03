package com.template.coe.demo.controller


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@AutoConfigureMockMvc
@WebMvcTest
class TestResourceTest extends Specification {

    @Autowired
    private MockMvc mockMvc;

    def "/api/test 를 호출하면 정상결과로 200을 넘기고, 컨텐츠는 'Hello I'm testing controller' 가 반환된다."() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test"))
                .andReturn()
                .response
                .contentAsString == "Hello I'm testing controller"
    }

    def "when set and delete are performed then the response has status 204 and content changes as expected"() {
        given: "a new name"
        def NAME = "Emmy"

        when: "삭제 요청이 들어간경우 "
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/test"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())

        then: "기본값이 전달된다. "
        mockMvc.perform(MockMvcRequestBuilders.get("/api/test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .response
                .contentAsString == "Hello I'm testing controller"
    }
}