package com.softeer.caart.global.config;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingConfig {
	private static final Gson GSON = new Gson();
	private static final String STR_CLASS_METHOD = "{0}.{1}({2})";
	private static final String STR_START_EXECUTE_TIME = "[{}] START";
	private static final String STR_END_EXECUTE_TIME = "[{}] FINISH // Return({}) : {}";

	@Around("execution(* com.softeer..*Controller.*(..))")
	public Object loggingInApi(final ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		final String formatClassMethod = MessageFormat.format(STR_CLASS_METHOD,
			pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(),
			this.getArgumentNames(pjp.getArgs()));

		try {
			log.info(STR_START_EXECUTE_TIME, formatClassMethod);

			// actual process
			retVal = pjp.proceed();

			log.info(STR_END_EXECUTE_TIME, formatClassMethod,
				((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName(),
				StringUtils.defaultString(GSON.toJson(retVal), "null"));
		} catch (Throwable e) {
			log.warn("[{}]\n{}", formatClassMethod, ExceptionUtils.getStackTrace(e));
			throw e;
		}

		return retVal;
	}

	@Around("execution(* com.softeer..*Service.*(..))")
	public Object loggingInService(final ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = null;
		final String formatClassMethod = MessageFormat.format(STR_CLASS_METHOD,
			pjp.getTarget().getClass().getSimpleName(), pjp.getSignature().getName(),
			this.getArgumentNames(pjp.getArgs()));

		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			log.info(STR_START_EXECUTE_TIME, formatClassMethod);

			// actual process
			retVal = pjp.proceed();

			stopWatch.stop();
			log.info(STR_END_EXECUTE_TIME, formatClassMethod,
				((MethodSignature)pjp.getSignature()).getReturnType().getSimpleName(),
				StringUtils.defaultString(GSON.toJson(retVal), "null"));
			log.info("EXECUTION TIME : {} ms", stopWatch.getTotalTimeMillis());
		} catch (Throwable e) {
			log.warn("[{}]\n{}", formatClassMethod, ExceptionUtils.getStackTrace(e));
			throw e;
		}

		return retVal;
	}

	private String getArgumentNames(final Object[] obj) {
		final List<String> list = new ArrayList<>();
		for (int i = 0; i < obj.length; i++) {
			if (obj[i] != null) {
				list.add(obj[i].getClass().getSimpleName());
			}
		}

		return StringUtils.join(list, ",");
	}
}
