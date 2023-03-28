package com.example.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MyCounterTest {
    @DisplayName("FixedThreadPool 을 생성한다.")
    @Test
    void testCounterWithConcurrencyFixed() throws InterruptedException {
        int numberOfThreads = 18;
        ExecutorService service = Executors.newFixedThreadPool(5);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        MyCounter counter = new MyCounter();
        iterateThread(numberOfThreads, service, latch, counter);

        assertThat(((ThreadPoolExecutor) service).getPoolSize()).isEqualTo(5);
    }

    private void iterateThread(int numberOfThreads, ExecutorService service, CountDownLatch latch, MyCounter counter) throws InterruptedException {
        for (int i = 0; i < numberOfThreads; i++) {
            service.submit(() -> {
                counter.increment();
                latch.countDown();
                throw new IllegalArgumentException();
            });
        }
        latch.await();
    }

    // ExecutorService 인터페이스의 구현 객체를 정적 팩토리 메서드로 제공하는 Executors 클래스의 세 가지 메소드 중 하나를 이용하여 스레드 풀을 쉽게 생성할 수 있다.
    // 첫번째 방법 Executors.newFixedThreadPool(int nThreads) 이용한다
    //파라미터로 제공되는 n 개 만큼 스레드 풀을 생성한다. 보통 일정량의 업무가 발생할 때 사용한다.
    //다음 예시는 18개의 스레드가 필요한 Task 를 제공하고 5개의 스레드 풀로 처리하는 과정을 확인해 보는 테스트다.

    @DisplayName("CachedThreadPool 을 생성한다.")
    @Test
    void testCounterWithConcurrencyCached() throws InterruptedException {
        int numberOfThreads = 18;
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        MyCounter counter = new MyCounter();
        iterateThread(numberOfThreads, service, latch, counter);

        assertThat(counter.getCount()).isEqualTo(numberOfThreads);
        assertThat(((ThreadPoolExecutor) service).getPoolSize()).isEqualTo(18);
        Thread.sleep(60000); // 60초 후 생성된 스레드가 제거되는지 확인한다.
        assertThat(((ThreadPoolExecutor) service).getPoolSize()).isEqualTo(0);
    }

    //Executors.newCachedThreadPool() 에 대해 알아본다.
    // 초기 스레드 개수가 0개로 설정되며 스레드 개수보다 많은 양의 작업의 요청되면 새로운 스레드를 생성하여 작업을 처리한다.
    // 작업이 끝난 스레드가 60초 이상 새로운 작업요청이 없으면 스레드를 종료하고 스레드 풀에서 제거된다.
    // 다음 예시는 18개의 스레드가 필요한 Task 를 제공하고 5개의 스레드 풀로 처리하는 과정을 확인해 보는 테스트다.
    // 위에 생성해둔 코드에 이어서 작성해서 테스트를 진행하면 된다.



}