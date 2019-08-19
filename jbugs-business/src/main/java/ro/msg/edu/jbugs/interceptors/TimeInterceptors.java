package ro.msg.edu.jbugs.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class TimeInterceptors {

    private static final Logger logger = LogManager.getLogger(TimeInterceptors.class.getName());

    @AroundInvoke
    private Object doTimeMeasure(InvocationContext ic){

        Object object = null;

        try {
            System.out.println(ic.getTarget().toString() + " " + ic.getMethod().getName());
            Long timeBefore = System.currentTimeMillis();
            object = ic.proceed();
            Long timeAfter = System.currentTimeMillis();
            Long duration = timeAfter - timeBefore;
            logger.traceEntry("Duration of method ",ic.getMethod().getName(), duration);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(ic.getTarget().toString() + " " + ic.getMethod().getName());
        }

        System.out.println("Interceptor");
        return object;
    }
}