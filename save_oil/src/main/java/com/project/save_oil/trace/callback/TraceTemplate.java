package com.project.save_oil.trace.callback;

import com.project.save_oil.trace.TraceStatus;
import com.project.save_oil.trace.logtrace.LogTrace;

public class TraceTemplate {
	private final LogTrace trace;
	
	public TraceTemplate(LogTrace trace) {
		this.trace = trace;
	}
	
	public <T> T execute(String message, TraceCallback<T> callback) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			
			// 로직 호출 : 변하는 것
			T result = callback.call();
			
			trace.end(status);
			
			return result;
		} catch(Exception e) {
			throw e;
		}
	}
	
}
