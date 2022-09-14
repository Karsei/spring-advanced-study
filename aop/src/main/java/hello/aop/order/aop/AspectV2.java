package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {
    // hello.aop.order 패키지와 하위 패키지
    // pointcut signature
    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder() {}


    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        // join point signature
        log.info("[log] {}", joinPoint.getSignature());

        return joinPoint.proceed();
    }
}
