package com.project.save_oil.threadProb;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadProbService {
	private ThreadLocal<String> succes = new ThreadLocal<>();
	
	public void startEvent(String name) {
		log.info("이벤트 참여={} -> 당첨={}", name, succes.get());
		succes.set(name);;
		sleep(1000);
		log.info("당첨자={}", succes.get());
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
