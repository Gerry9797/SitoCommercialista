package com.commercialista.backend.log;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class CustomConsoleAppender extends ch.qos.logback.core.ConsoleAppender<ILoggingEvent> {

	@Override
	protected void append(ILoggingEvent eventObject) {

		if (!"true".equals(eventObject.getMDCPropertyMap().get(LogUtil.do_not_log))) {
			super.append(eventObject);
		}

	}

}
