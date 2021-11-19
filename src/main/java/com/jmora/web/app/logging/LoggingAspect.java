package com.jmora.web.app.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {
	public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	/**
	 * This method uses Around advice which ensures that an advice can run before
	 * and after the method execution, to and log the execution time of the method
	 * This advice will be be applied to all the method which are annotate with the
	 * annotation @LogExecutionTime @see com.example.springaop.logging.LogExecutionTime
	 * 
	 * Any mehtod where execution times need to be measue and log, anotate the method with @LogExecutionTime
	 * example 
	 * @LogExecutionTime
	 * public void m1();
	 * 
	 * @param proceedingJoinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jmora.web.app.logging.LogExecutionTime)")
	public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

		// Get intercepted method details
		String className = methodSignature.getDeclaringType().getSimpleName();
		String methodName = methodSignature.getName();

		// Measure method execution time
		StopWatch stopWatch = new StopWatch(className + "->" + methodName);
		stopWatch.start(methodName);
		Object result = proceedingJoinPoint.proceed();
		stopWatch.stop();
		
		// Log method execution time
		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder();
			
			sb.append(" took total: " ).append(stopWatch.getTotalTimeSeconds()).append(" seconds");
			
			// prettyPrint() return a string with a table describing all tasks performed. For custom reporting, call getTaskInfo() and use the
			// task info directly.
			sb.append("\n1. prettyPrint Result: ").append( stopWatch.prettyPrint());
			
			// Return a short description of the total running time.
			sb.append("\n2. Short Summary: ").append( stopWatch.shortSummary());
			
			// Return the number of tasks timed.
			sb.append("\n3. Total Task Count: ").append(stopWatch.getTaskCount());
			
			// Return the name of this task.
			sb.append("\n4. Last Task Name: ").append(stopWatch.getLastTaskInfo().getTaskName());
			
			logger.info("{}",sb);
		}
		return result;
	}
}
