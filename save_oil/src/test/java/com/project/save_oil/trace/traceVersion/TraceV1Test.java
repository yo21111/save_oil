package com.project.save_oil.trace.traceVersion;

import org.junit.jupiter.api.Test;

import com.project.save_oil.trace.TraceStatus;

public class TraceV1Test {
	
	@Test
	void begin_end() {
		TraceV1 trace = new TraceV1();
		TraceStatus status = trace.begin("hello");
		trace.end(status);
	}
	
	@Test
	void begin_exception() {
		TraceV1 trace = new TraceV1();
		TraceStatus status = trace.begin("hello");
		trace.exception(status, new IllegalStateException());
	}
}
