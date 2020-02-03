package com.template.coe.demo;

import com.template.coe.demo.controller.TestResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spock.lang.Specification;

@SpringBootTest
class DemoApplicationTests extends Specification {

	@Autowired (required = false)
	TestResource testResource;



}
