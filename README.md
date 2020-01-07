# Logging 

## Logging Pattern 

[Logger Layout](http://logback.qos.ch/manual/layouts.html)

| 패턴 | 설명 |
| :--- | :--- |
| %Logger{length} | Logger name을 축약할 수 있다. {length}는 최대 자리 수 |
| %thread | 현재 Thread 이름 |
| %-5level | 로그 레벨, -5는 출력의 고정폭 값 |
| %msg | 로그 메시지 (=%message) |
| %n | new line |
| ${PID:-} | 프로세스 아이디 |
| %d | 로그 기록시간 |
| %p |  로깅 레벨 |
| %F | 로깅이 발생한 프로그램 파일명 |
| %M | 로깅일 발생한 메소드의 이름 |
| %l | 로깅이 발생한 호출지의 정보 |
| %L | 로깅이 발생한 호출지의 라인 수 |
| %t | 쓰레드 명 |
| %c | 로깅이 발생한 카테고리 |
| %C | 로깅이 발생한 클래스 명 |
| %m | 로그 메시지 |
| %r | 애플리케이션 시작 이후부터 로깅이 발생한 시점까지의 시간|

