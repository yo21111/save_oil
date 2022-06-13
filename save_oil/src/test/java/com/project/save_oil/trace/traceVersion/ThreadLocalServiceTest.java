package com.project.save_oil.trace.traceVersion;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalServiceTest {

	private ThreadLocalService service = new ThreadLocalService();

	@Test
	void field() {
		log.info("main Start");
		Runnable userA = () -> {
			service.logic("userA");
		};
		Runnable userB = () -> {
			service.logic("userB");
		};

		Thread threadA = new Thread(userA);
		threadA.setName("thread-A");
		Thread threadB = new Thread(userB);
		threadB.setName("thread-B");

		threadA.start();
//		sleep(2000);  // 동시성 문제가 안하는 코드 (서비스에서 1초만 쉬고 동작)
		sleep(100); // 동시성 문제가 발생하는 코드
		threadB.start();
		
		sleep(3000);
		log.info("main exit");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
