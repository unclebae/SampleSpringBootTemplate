# Hazelcast 설정하기. 

서버 프로그래밍에서 성능향상을 위한 개선 포인트중에서 캐시는 효과는 매우 큽니다. 

일반적으로 스프링 부트에서는 EHCahce 와 같은 로컬 캐시를 기본적으로 지원합니다. 

## 로컬 캐시 설정하기. 

일단 https://start.spring.io 에서 프로젝트 하나를 생성합니다. 

의존성은 spring-starter-web 을 포함한 설정으로 프로젝트를 다운로드 받습니다. 

```$xslt
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
```

그리고 로컬 캐시가 동작하도록 다음과 같이 설정해줍니다. 

src/main/java/com/template/coe/demo/DemoApplication.java

파일에서 아래와 같이 작업해줍니다. 

```$xslt
@EnableCaching
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

```

@EnableCaching 을 추가해주면 캐시를 사용하겠다는 의미입니다. 

### 캐시 설정 피로지토리 생성하기. 

우리는 임의의 리포지토리를 하나 만들어 보겠습니다. 

단순히 파라미터를 받아서 그대로 반납하는 코드입니다. 

```$xslt
package com.template.coe.demo.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class HazelcastRepository {

    @Cacheable("cache-test")
    public String getCache(String id) {
        System.out.println("Insert Id: " + id);
        return "Return Echo: " + id;
    }
}

```

@Cacheable("캐시이름") 을 걸어주면 캐시 됩니다. 

### 컨트롤러 생성하기. 

/src/main/java/com/template/coe/demo/resources/TestResource.java 파일을 만들고 아래와 같이 작업해줍니다. 

```$xslt
package com.template.coe.demo.resources;

import com.template.coe.demo.repositories.HazelcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @Autowired
    private HazelcastRepository hazelcastRepository;

    @GetMapping("/api/{id}")
    public String getCache(@PathVariable("id") String id) {
        return hazelcastRepository.getCache(id);
    }
}

```
피토지토리 이름을 HazelcastRepository 라고 이름지었습니다. 앞으로 Hazelcast 를 이용할거라서 미리 만들었습니다. 

다만 아직까지는 로컬 캐시를 이용하고 있습니다. 

실행한 결과를 확인해보면 아래와 같은 메시지만 노출됩니다.

```$xslt
curl http://localhost:9000/api/test
``` 


```$xslt
2020-01-29 12:57:58.043  INFO 20034 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms
2020-01-29 12:57:58.089  INFO 20034 --- [nio-9000-exec-1] c.h.i.p.impl.PartitionStateManager       : [172.23.25.102]:5704 [TestGroup] [3.12.4] Initializing cluster partition table arrangement...
Insert Id: test
```

여러번 호출해도 Insert Id: test 가 한번만 나타납니다. 

그리고 이번에는 HazelcastRepository.java 파일에서 @Cachable 부분을 주석처리하고 다시 실행해 봄니다. 

```$xslt
2020-01-29 12:59:52.405  INFO 20550 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-01-29 12:59:52.412  INFO 20550 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 7 ms
Insert Id: test
Insert Id: test
Insert Id: test
```

캐시되지 않았음을 확인할 수 있습니다. 

## Hazelcast 설정하기. 

Hazelcast 는 분산 캐시입니다. 다만 분산캐시이지만 Java 기반으로 서버가 올라가면 Hazelcast 가 올라오며, 그룹명을 기준으로 클러스터링 됩니다. 

즉, 서버가 시작될 때마다. Hazelcast 에 등록되어 클러스터를 구성하게 됩니다. 

### 의존성 추가하기. 

이제는 스프링부트를 위한 의존성을 아래와 같이 추가해 줍니다. 

```$xslt
		<dependency>
			<groupId>com.hazelcast</groupId>
			<artifactId>hazelcast</artifactId>
		</dependency>

		<dependency>
			<groupId>com.hazelcast</groupId>
			<artifactId>hazelcast-spring</artifactId>
		</dependency>
```
### 설정하기 

이제는 src/main/java/com/template/coe/demo/configs/CacheConfig.java 파일을 하나 만들고 아래와 같이 작업해줍니다. 

```$xslt
package com.template.coe.demo.configs;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.getGroupConfig().setName("TestGroup");
        config.setInstanceName("hazelcast-instance-version1")
                .addMapConfig(
                        new MapConfig()
                        .setName("hazelConfigName")
                        .setMaxSizeConfig(
                                new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE)
                        )
                        .setEvictionPolicy(EvictionPolicy.LFU)
                        .setTimeToLiveSeconds(0)
                );
        return config;
    }
}

```

hazelcastConfig 를 빈으로 등록합니다. 

groupConfig 의 이름을 지정해주면, 분산 캐시의 그룹을 지정해 줄 수 있습니다. 프로젝틀에서는 이 그룹이름을 꼭 유니크하게 작업해주는 것을 추천합니다. 

그리고 MapConfig 을 통해서 저장될 맵에 대한 설정을 해줍니다. 이름을 지정하면 map 의 이름을 잡아주는 것입니다. 

MaxSizeConfig 는 map 의 용량을 지정합니다. 이때 최대 크기는 MaxSizeConfig 을 통해서 설정합니다. 

MaxSizeConfig 은 맵에 저장될 데이터의 크기를 지정합니다. MazisePolicy 는 저장할 데이터 용량에 대한 설정값으로 FREE_HEAP_SIZE 은 JVM 의 힙 사이트를 최대한 이용합니다. 

EvictionPolicy 는 저장된 데이터를 어떻게 비워줄지에 대한 설정으로 최근 사용한것 우선, 가장 오래된것 우선, 없음, 랜덤 등으로 지정이 가능합니다. 

TimeToLiveSeconds 는 캐시에 저장될 시간을 나타내는 것입니다. 0 ~ 무한대 로 0으로 지정하면 서버가 내려가기 전까지 삭제 되지 않습니다. (기본값은 0 입니다)

### 서버 실행하기.

서버를 실행하면 다음과 같이 뜹니다. 

```$xslt
Members {size:1, ver:1} [
	Member [172.23.25.102]:5701 - c32592fa-8e7b-49e3-8dcb-97b943d105ac this
]
``` 

어려대의 서버를 올리면 아래와 같이 나타납니다. 

```$xslt
Members {size:3, ver:9} [
	Member [172.23.25.102]:5701 - c32592fa-8e7b-49e3-8dcb-97b943d105ac this
	Member [172.23.25.102]:5702 - 02aa5d50-990a-4ddc-ba11-66185c15bee1
	Member [172.23.25.102]:5703 - 2c269349-bc12-45f1-9dfd-1a280ffcc8c7
]
```

보시는바와 같이 this 는 현재 서버에 있는 클러스터 노드를 나타내며, 3개의 인스턴스가 올라와서 분산 캐시 그룹을 이루었음을 확인할 수 있습니다. 

9000 포트 9001포트 9002 포트를 각각 실행하여 요청을 보내면 


```$xslt
2020-01-29 12:57:58.043  INFO 20034 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms
2020-01-29 12:57:58.089  INFO 20034 --- [nio-9000-exec-1] c.h.i.p.impl.PartitionStateManager       : [172.23.25.102]:5704 [TestGroup] [3.12.4] Initializing cluster partition table arrangement...
Insert Id: test
```

와 같이 한번만 실행됩니다. 

9002 에서 http://localhost:9002/api/test 를 싱행해도 Insert Id: test 라는 내용이 콘솔이 찍히지 않습니다. 

즉, 분산 캐시를 이용하여 호출되었음을 확인할 수 있습니다. 

## 결론

지금까지 분산 캐시를 이용하는 방법을 알아 보Hazelcast 설정하기.
서버 프로그래밍에서 성능향상을 위한 개선 포인트중에서 캐시는 효과는 매우 큽니다.

일반적으로 스프링 부트에서는 EHCahce 와 같은 로컬 캐시를 기본적으로 지원합니다.

로컬 캐시 설정하기.
일단 https://start.spring.io 에서 프로젝트 하나를 생성합니다.

의존성은 spring-starter-web 을 포함한 설정으로 프로젝트를 다운로드 받습니다.

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
그리고 로컬 캐시가 동작하도록 다음과 같이 설정해줍니다.

src/main/java/com/template/coe/demo/DemoApplication.java

파일에서 아래와 같이 작업해줍니다.

@EnableCaching
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@EnableCaching 을 추가해주면 캐시를 사용하겠다는 의미입니다.

캐시 설정 피로지토리 생성하기.
우리는 임의의 리포지토리를 하나 만들어 보겠습니다.

단순히 파라미터를 받아서 그대로 반납하는 코드입니다.

package com.template.coe.demo.repositories;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class HazelcastRepository {

    @Cacheable("cache-test")
    public String getCache(String id) {
        System.out.println("Insert Id: " + id);
        return "Return Echo: " + id;
    }
}

@Cacheable("캐시이름") 을 걸어주면 캐시 됩니다.

컨트롤러 생성하기.
/src/main/java/com/template/coe/demo/resources/TestResource.java 파일을 만들고 아래와 같이 작업해줍니다.

package com.template.coe.demo.resources;

import com.template.coe.demo.repositories.HazelcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestResource {

    @Autowired
    private HazelcastRepository hazelcastRepository;

    @GetMapping("/api/{id}")
    public String getCache(@PathVariable("id") String id) {
        return hazelcastRepository.getCache(id);
    }
}

피토지토리 이름을 HazelcastRepository 라고 이름지었습니다. 앞으로 Hazelcast 를 이용할거라서 미리 만들었습니다.

다만 아직까지는 로컬 캐시를 이용하고 있습니다.

실행한 결과를 확인해보면 아래와 같은 메시지만 노출됩니다.

curl http://localhost:9000/api/test
2020-01-29 12:57:58.043  INFO 20034 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms
2020-01-29 12:57:58.089  INFO 20034 --- [nio-9000-exec-1] c.h.i.p.impl.PartitionStateManager       : [172.23.25.102]:5704 [TestGroup] [3.12.4] Initializing cluster partition table arrangement...
Insert Id: test
여러번 호출해도 Insert Id: test 가 한번만 나타납니다.

그리고 이번에는 HazelcastRepository.java 파일에서 @Cachable 부분을 주석처리하고 다시 실행해 봄니다.

2020-01-29 12:59:52.405  INFO 20550 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'
2020-01-29 12:59:52.412  INFO 20550 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 7 ms
Insert Id: test
Insert Id: test
Insert Id: test
캐시되지 않았음을 확인할 수 있습니다.

Hazelcast 설정하기.
Hazelcast 는 분산 캐시입니다. 다만 분산캐시이지만 Java 기반으로 서버가 올라가면 Hazelcast 가 올라오며, 그룹명을 기준으로 클러스터링 됩니다.

즉, 서버가 시작될 때마다. Hazelcast 에 등록되어 클러스터를 구성하게 됩니다.

의존성 추가하기.
이제는 스프링부트를 위한 의존성을 아래와 같이 추가해 줍니다.

		<dependency>
			<groupId>com.hazelcast</groupId>
			<artifactId>hazelcast</artifactId>
		</dependency>

		<dependency>
			<groupId>com.hazelcast</groupId>
			<artifactId>hazelcast-spring</artifactId>
		</dependency>
설정하기
이제는 src/main/java/com/template/coe/demo/configs/CacheConfig.java 파일을 하나 만들고 아래와 같이 작업해줍니다.

package com.template.coe.demo.configs;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfig {

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.getGroupConfig().setName("TestGroup");
        config.setInstanceName("hazelcast-instance-version1")
                .addMapConfig(
                        new MapConfig()
                        .setName("hazelConfigName")
                        .setMaxSizeConfig(
                                new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE)
                        )
                        .setEvictionPolicy(EvictionPolicy.LFU)
                        .setTimeToLiveSeconds(0)
                );
        return config;
    }
}

hazelcastConfig 를 빈으로 등록합니다.

groupConfig 의 이름을 지정해주면, 분산 캐시의 그룹을 지정해 줄 수 있습니다. 프로젝틀에서는 이 그룹이름을 꼭 유니크하게 작업해주는 것을 추천합니다.

그리고 MapConfig 을 통해서 저장될 맵에 대한 설정을 해줍니다. 이름을 지정하면 map 의 이름을 잡아주는 것입니다.

MaxSizeConfig 는 map 의 용량을 지정합니다. 이때 최대 크기는 MaxSizeConfig 을 통해서 설정합니다.

MaxSizeConfig 은 맵에 저장될 데이터의 크기를 지정합니다. MazisePolicy 는 저장할 데이터 용량에 대한 설정값으로 FREE_HEAP_SIZE 은 JVM 의 힙 사이트를 최대한 이용합니다.

EvictionPolicy 는 저장된 데이터를 어떻게 비워줄지에 대한 설정으로 최근 사용한것 우선, 가장 오래된것 우선, 없음, 랜덤 등으로 지정이 가능합니다.

TimeToLiveSeconds 는 캐시에 저장될 시간을 나타내는 것입니다. 0 ~ 무한대 로 0으로 지정하면 서버가 내려가기 전까지 삭제 되지 않습니다. (기본값은 0 입니다)

서버 실행하기.
서버를 실행하면 다음과 같이 뜹니다.

Members {size:1, ver:1} [
	Member [172.23.25.102]:5701 - c32592fa-8e7b-49e3-8dcb-97b943d105ac this
]
여러대의 서버를 올리면 아래와 같이 나타납니다.

Members {size:3, ver:9} [
	Member [172.23.25.102]:5701 - c32592fa-8e7b-49e3-8dcb-97b943d105ac this
	Member [172.23.25.102]:5702 - 02aa5d50-990a-4ddc-ba11-66185c15bee1
	Member [172.23.25.102]:5703 - 2c269349-bc12-45f1-9dfd-1a280ffcc8c7
]
보시는바와 같이 this 는 현재 서버에 있는 클러스터 노드를 나타내며, 3개의 인스턴스가 올라와서 분산 캐시 그룹을 이루었음을 확인할 수 있습니다.

9000 포트 9001포트 9002 포트를 각각 실행하여 요청을 보내면

2020-01-29 12:57:58.043  INFO 20034 --- [nio-9000-exec-1] o.s.web.servlet.DispatcherServlet        : Completed initialization in 9 ms
2020-01-29 12:57:58.089  INFO 20034 --- [nio-9000-exec-1] c.h.i.p.impl.PartitionStateManager       : [172.23.25.102]:5704 [TestGroup] [3.12.4] Initializing cluster partition table arrangement...
Insert Id: test
와 같이 한번만 실행됩니다.

9002 에서 http://localhost:9002/api/test 를 싱행해도 Insert Id: test 라는 내용이 콘솔이 찍히지 않습니다.

즉, 분산 캐시를 이용하여 호출되었음을 확인할 수 있습니다.

결론
지금까지 분산 캐시를 이용하는 방법을 알아 보았습니다.

Hazelcast 는 별도의 서버를 올리지 않고, 현재 인스턴스를 서버로 해서 분산 캐시를 구성할 수 있다는 것에서 간단하면서도 매우 유용한 솔루션을 제공합니다.

그러나 캐싱을 위한 메모리를 많이 먹으므로 이 부분을 시스템 설계시 꼭 고민해야합니다.았습니다. 

Hazelcast 는 별도의 서버를 올리지 않고, 현재 인스턴스를 서버로 해서 분산 캐시를 구성할 수 있다는 것에서 간단하면서도 매우 유용한 솔루션을 제공합니다. 

그러나 캐싱을 위한 메모리를 많이 먹으므로 이 부분을 시스템 설계시 꼭 고민해야합니다. 