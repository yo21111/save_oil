package com.project.save_oil.trace.logtrace;

import com.project.save_oil.trace.TraceStatus;

public interface LogTrace {
	TraceStatus begin(String message);
	void end(TraceStatus status);
	void exception(TraceStatus status, Exception e);
}
