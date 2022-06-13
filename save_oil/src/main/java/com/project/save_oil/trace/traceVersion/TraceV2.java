package com.project.save_oil.trace.traceVersion;

import org.springframework.stereotype.Component;

import com.project.save_oil.trace.TraceId;
import com.project.save_oil.trace.TraceStatus;

import lombok.extern.slf4j.Slf4j;
 
@Slf4j
@Component
public class TraceV2 {

	private static final String START_PREFIX = "-->";
	private static final String COMPLETE_PREFIX = "<--";
	private static final String EX_PREFIX = "<X-";
	
	// 최초 호출시 사용
	public TraceStatus begin(String message) {
		TraceId traceId = new TraceId();
		Long startTimeMs = System.currentTimeMillis();
		log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
		return new TraceStatus(traceId, startTimeMs, message);
	}
	
	// 2번째 이상 시 활용
	public TraceStatus beginSync(TraceId beforeTraceId, String message) {
		// 이전 traceId를 바탕으로 요청 트랜잭션 아이디는 유지, 레벨만 +1
		TraceId nextId = beforeTraceId.createNextId();
		Long startTimeMs = System.currentTimeMillis();
		log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
		return new TraceStatus(nextId, startTimeMs, message);
	}

	public void end(TraceStatus status) {
		complete(status, null);
	}

	public void exception(TraceStatus status, Exception e) {
		complete(status, e);
	}

	private void complete(TraceStatus status, Exception e) {
		Long stopTimeMs = System.currentTimeMillis();
		long resultTimeMs = stopTimeMs - status.getStartTimeMs();
		TraceId traceId = status.getTraceId();
		if (e == null) {
			log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()),
					status.getMessage(), resultTimeMs);
		} else {
			log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()),
					status.getMessage(), resultTimeMs, e.toString());
		}
	}
	
	// level=0
	// level=1 |-->
	// level=2 |	|-->
	
	//level=2 ex |	|<x-
	//level=1 ex |<x-
	private static String addSpace(String prefix, int level) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < level; i++) {
			sb.append((i == level - 1) ? "|" + prefix : "| ");
		}
		return sb.toString();
	}
}