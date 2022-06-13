package com.project.save_oil.trace.traceVersion;

import org.junit.jupiter.api.Test;

import com.project.save_oil.trace.TraceStatus;

public class TraceV2Test {
	
	@Test
	void begin_end() {
		TraceV2 trace = new TraceV2();
		TraceStatus status1 = trace.begin("hello1");
		TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
		trace.end(status2);
		trace.end(status1);
	}
	
	@Test
	void begin_exception() {
		TraceV2 trace = new TraceV2();
		TraceStatus status1 = trace.begin("hello1");
		TraceStatus status2 = trace.beginSync(status1.getTraceId(), "hello2");
		trace.exception(status2, new IllegalStateException());
		trace.exception(status1, new IllegalStateException());
	}
}
