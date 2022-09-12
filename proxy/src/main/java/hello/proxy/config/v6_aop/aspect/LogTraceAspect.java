package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogTraceAspect {
    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    // advisor
    @Around("execution(* hello.proxy.app..*(..))") // <- pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable { // <- advice
        TraceStatus status = null;
        try {
//            joinPoint.getTarget(); // 실제 호출 대상
//            joinPoint.getArgs(); // 전달 인자
//            joinPoint.getSignature(); // join point 시그니처
            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);
            // target 호출
            Object result = joinPoint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
