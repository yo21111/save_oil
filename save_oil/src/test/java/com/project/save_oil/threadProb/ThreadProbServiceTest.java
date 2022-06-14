package com.project.save_oil.threadProb;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadProbServiceTest {
	private ThreadProbService service = new ThreadProbService();
	
	@Test
	void event() {
		log.info("event 시작");
		Runnable userA = () -> {
			service.startEvent("userA");
		};
		Runnable userB = () -> {
			service.startEvent("userB");
		};
		
		Thread thA = new Thread(userA);
		thA.setName("thread-A");
		Thread thB = new Thread(userB);
		thB.setName("thread-B");
		
		thA.start();
		sleep(100);
		thB.start();
		
		sleep(3000);
		log.info("event 끝");
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
