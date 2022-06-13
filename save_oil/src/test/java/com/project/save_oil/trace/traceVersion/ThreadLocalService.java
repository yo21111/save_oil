package com.project.save_oil.trace.traceVersion;

import lombok.extern.slf4j.Slf4j;

// 동시성 문제 테스트를 위한 서비스 클래스
@Slf4j
public class ThreadLocalService {

	private ThreadLocal<String> nameStore = new ThreadLocal<>();

	public String logic(String name) {
		log.info("저장 name={} -> nameStore={}", name, nameStore.get());
		nameStore.set(name);;
		sleep(1000);
		log.info("조회 nameStore={}", nameStore.get());
		return nameStore.get();
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
