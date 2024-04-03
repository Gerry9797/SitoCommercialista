package com.commercialista.backend.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;

public class CustomFileAppender extends RollingFileAppender<ILoggingEvent> {

	@Override
	protected void append(ILoggingEvent eventObject) {

		if (!"true".equals(eventObject.getMDCPropertyMap().get(LogUtil.do_not_log))) {
			super.append(eventObject);
		}

	}

}
