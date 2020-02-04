package com.template.coe.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * 스프링 부트의 경우 설정정보 우선순위는 다음과 같다.
 * 1. 명령행 인자.
 * 2. java:comp/env 에 있는 JDNI
 * 3. System.getProperties() 으로 읽어오는 속성
 * 4. 운영체제의 환경변수
 * 5. jar 외부에 존재하는 application.properties 파일이나, application.yml 파일에 정의된 속성
 * 6. jar 내부에 존재하는 application.properties 파일이나 application.yml 파일에 정의된 속성
 * 7. @Configuration dl qnxdms zmffotmdp @PropertySource 으로 지정된 곳에 있는 속성
 * 8. SpringApplication.getDefaultProperties() 로 읽어올 수 있는 기본값.
 */
@Configuration
@SpringBootApplication
public class DemoApplication {

	@Bean
	static PropertySourcesPlaceholderConfigurer pspc() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


}
