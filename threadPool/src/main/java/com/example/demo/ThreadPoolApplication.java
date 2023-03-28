package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ThreadPoolApplication {

	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);

		System.out.println("첫 번째 작업이 실행됩니다.");
		service.schedule(() -> System.out.println("두 번째 작업이 실행됩니다."), 5, TimeUnit.SECONDS);
		System.out.println("세 번째 작업이 실행됩니다.");

		service.shutdown();
	}
	//Executors.newScheduledThreadPool(int corePoolSize)
	//스레드를 일정시간이 흐르고 난 뒤 실행시키도록 하는 스케줄링 스레드 기능이다.
	//해당 기능을 테스트해 보기 위해서는 테스트 코드가 아닌 메인에서 실행해봐야 한다. 따라서 Sample 클래스를 만들고 실습을 해본다.
	//corePoolSize 는 생성할 corePool 의 크기를 지정해주는 부분인데 Executors.newScheduledThreadPool(0) 을 하더라도 실행에는 문제가 없어 보인다.
	//다만 JDK 8 버전 이하에서 발견된 버그로 단일 코어 가상 머신에서 CPU 를 100% 사용하는 버그가 있기 때문에 파라미터로 1 이상으로 설정한다.

	//자바에서 사용하게 될 스레드 풀에 대해 간단하게 알아봤다.
	//얼마만큼의 스레드가 주기적으로 소모될지, 스레드를 추가로 생성할지, 작업 후 반환 값을 받을지에 대한 분석을 토대로 상황에 적합한 Thread 및 ThreadPool 을 적용한다면 멀티 코어 프로그래밍에 큰 도움이 된다고 생각한다.
	// 다만, 적절하지 않게 사용할 경우 오히려 사용하지 않은 것보다 못할 수 있기 때문에 주의해야 한다.
}
