package fr.eni.encheres.configuration;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogbackFilter extends Filter<ILoggingEvent> {

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event	.getLoggerName()
					.startsWith("fr.eni.encheres")) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.DENY;
		}
	}
}