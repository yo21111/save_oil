package com.project.save_oil.trace.template;

import com.project.save_oil.trace.TraceStatus;
import com.project.save_oil.trace.logtrace.LogTrace;

// 템플릿 메서드 패턴
public abstract class AbstractTemplate<T> {
	private final LogTrace trace;
	
	public AbstractTemplate(LogTrace trace) {
		this.trace = trace;
	}
	
	public T execute(String message) {
		TraceStatus status = null;
		try {
			status = trace.begin(message);
			
			// 로직 호출 : 변하는 것
			T result = call();
			
			trace.end(status);
			
			return result;
		} catch(Exception e) {
			throw e;
		}
	}
	
	// 변하는 부분은 상속으로 구현
	protected abstract T call();
}
