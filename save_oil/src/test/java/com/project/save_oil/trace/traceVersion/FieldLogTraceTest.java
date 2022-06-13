package com.project.save_oil.trace.traceVersion;

import java.nio.channels.IllegalSelectorException;

import org.junit.jupiter.api.Test;

import com.project.save_oil.trace.TraceStatus;
import com.project.save_oil.trace.logtrace.FieldLogTrace;

public class FieldLogTraceTest {
	
	FieldLogTrace trace = new FieldLogTrace();
	
	@Test
	public void begin_end_level2() {
		TraceStatus status1 = trace.begin("hello1");
		TraceStatus status2 = trace.begin("hello2");
		trace.end(status2);
		trace.end(status1);
	}
	
	@Test
	public void begin_exception_level2() {
		TraceStatus status1 = trace.begin("hello1");
		TraceStatus status2 = trace.begin("hello2");
		trace.exception(status2, new IllegalSelectorException());
		trace.exception(status1, new IllegalSelectorException());
	}
}
