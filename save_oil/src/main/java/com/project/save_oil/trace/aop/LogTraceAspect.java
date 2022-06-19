package com.project.save_oil.trace.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.project.save_oil.trace.TraceStatus;
import com.project.save_oil.trace.logtrace.LogTrace;

@Aspect
@Component
public class LogTraceAspect {
	
	private final LogTrace logTrace;
	
	public LogTraceAspect(LogTrace logTrace) {
		this.logTrace = logTrace;
	}
	
	@Around("execution(* com.project.save_oil.Controller..*(..)) "
			+ "|| execution(* com.project.save_oil.api..*(..)) "
			+ "|| execution(* com.project.save_oil.board..*(..))"
			+ "|| execution(* com.project.save_oil.comment..*(..))"
			+ "|| execution(* com.project.save_oil.domain..*(..))"
			+ "|| execution(* com.project.save_oil.interceptor..*(..))"
			+ "|| execution(* com.project.save_oil.member..*(..))"
			+ "|| execution(* com.project.save_oil.service..*(..))"
			+ "|| execution(* com.project.save_oil.validation..*(..))")
	public Object execution(ProceedingJoinPoint joinPoint) throws Throwable {
		TraceStatus status = null;
		try {
			String message = joinPoint.getSignature().toShortString();
			status = logTrace.begin(message);
			
			Object result = joinPoint.proceed();
			logTrace.end(status);
			return result;
		} catch(Exception e) {
			logTrace.exception(status, e);
			throw e;
		}
	}
}
