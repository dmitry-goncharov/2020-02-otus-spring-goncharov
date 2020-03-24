package ru.gonch.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class CacheCardsAspect {
    private static final Logger log = LoggerFactory.getLogger(CacheCardsAspect.class);
    private Object cached;

    @Around("execution(public * ru.gonch.spring.dao.CardDao.getCards())")
    public Object cacheCards(ProceedingJoinPoint joinPoint) throws Throwable {
        if (cached == null) {
            cached = joinPoint.proceed();
            log.debug("Cards cached");
        }
        return cached;
    }
}
