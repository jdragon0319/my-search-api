package com.example.mysearchapi.common.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.*;

@Aspect
public class CollectLoggingAspect {
    private static ThreadLocal<String> threadId = new ThreadLocal<>();
    private static ThreadLocal<Boolean> checkRequestMappingThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<ProceedingJoinPoint> serviceJoinPointThreadLocal = new ThreadLocal<>();
    private static final Logger collectLogger = LoggerFactory.getLogger(CollectLogger.class);

    public static void reset() {
        checkRequestMappingThreadLocal.remove();
        threadId.remove();
        serviceJoinPointThreadLocal.remove();
    }

    @Around("@annotation(CollectLogging)")
    public Object collectControllerLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        setThreadId();

        if (isApiMethod(method)) {
            if (isFirstApiCall()) {
                checkRequestMappingThreadLocal.set(true);
                return loggingProceedApi(joinPoint, method);
            }
            return notLoggingProceedApi(joinPoint);
        }

        if (isServiceMethod()) {
            serviceJoinPointThreadLocal.set(joinPoint);
        }

        return loggingProceedService(joinPoint);
    }

    private Object loggingProceedService(ProceedingJoinPoint joinPoint) throws Throwable {
        ProceedResult result = proceed(joinPoint);
        Map<String, Object> paramMap = getParamMap(joinPoint);
        Map<String, Object> loggingMap = getServiceLoggingMap(paramMap, result);
        collectLogger.info("{}", CollectLogger.toJsonStr(loggingMap));
        return result.getReturnValue();
    }

    private Map<String, Object> getServiceLoggingMap(Map<String, Object> paramMap, ProceedResult result) {
        Map<String, Object> map = new HashMap<>();
        map.put("logType", "serviceCall");

        putValuesInner(map, paramMap, result);
        return map;
    }

    private boolean isServiceMethod() {
        return isFirstApiCall() && isFirstServiceCall();
    }

    private boolean isFirstServiceCall() {
        return serviceJoinPointThreadLocal.get() == null;
    }

    private Object notLoggingProceedApi(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    private Object loggingProceedApi(ProceedingJoinPoint joinPoint, Method method) throws Throwable {
        ProceedResult result = proceed(joinPoint);
        CollectLogging collectLogging = method.getAnnotation(CollectLogging.class);
        ProceedingJoinPoint loggedJoinPoint = Optional.ofNullable(serviceJoinPointThreadLocal.get()).orElse(joinPoint);

        Map<String, Object> paramMap = getParamMap(loggedJoinPoint);
        Map<String, Object> loggingMap = getApiLoggingMap(paramMap, collectLogging, result);
        collectLogger.info("{}", CollectLogger.toJsonStr(loggingMap));
        return result.getReturnValue();
    }

    private Map<String, Object> getApiLoggingMap(Map<String, Object> paramMap, CollectLogging collectLogging, ProceedResult result) {
        Map<String, Object> map = new HashMap<>();
        map.put("logType", "apiCall");
        map.put("menu", String.format("%s", collectLogging.menu()));
        map.put("resource", String.format("%s", collectLogging.resource()));
        map.put("method", String.format("%s", collectLogging.method()));
        putValuesInner(map, paramMap, result);
        return map;
    }

    private void putValuesInner(Map<String, Object> map, Map<String, Object> paramMap, ProceedResult result) {
        map.put("threadId", threadId.get());
        map.put("status", result.status);
        map.put("elapsedTime", result.elapsedTime);
        map.put("javaClassName", String.format("%s", result.joinPoint.getTarget().getClass().getName()));
        map.put("javaMethodName", String.format("%s", result.joinPoint.getSignature().getName()));

        if (paramMap != null) {
            map.put("params", paramMap);
        }
    }

    private Map<String, Object> getParamMap(ProceedingJoinPoint joinPoint) {
        return Optional.ofNullable(joinPoint)
                .map(JoinPoint::getArgs)
                .map(this::getJsonString)
                .map(JSONObject::new)
                .map(JSONObject::toMap)
                .map(m -> {
                    if (m.containsKey("loginPwd")) {
                        m.remove("loginPwd");
                    }
                    if (m.containsKey("clientSecret")) {
                        m.remove("clientSecret");
                    }
                    return m;
                }).orElse(null);
    }

    private String getJsonString(Object[] args) {
        ObjectMapper mapper = new ObjectMapper()
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .registerModule(new JavaTimeModule());
        try {
            if (args != null && args.length > 0) {
                String ret = mapper.writeValueAsString(args[0]);
                if (args[0] instanceof String)
                    ret = ret.substring(1, ret.length() - 1);
                return ret.startsWith("{") ? ret : String.format("{\"param1\":\"%s\"}", ret);
            }
            return "{}";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private ProceedResult proceed(ProceedingJoinPoint joinPoint) {
        String status = "success";
        Object ret = null;
        Throwable t = null;
        Instant start = Instant.now();
        try {
            ret = joinPoint.proceed();
        } catch (Throwable e) {
            t = e;
            status = "error";
        }
        final long elapsedTime = Duration.between(start, Instant.now()).toMillis();
        return new ProceedResult(joinPoint, status, ret, t, elapsedTime);
    }

    private boolean isFirstApiCall() {
        return !Optional.ofNullable(checkRequestMappingThreadLocal.get()).orElse(false);
    }

    private boolean isApiMethod(Method method) {
        Annotation[] annotations = method.getAnnotations();
        return Arrays.stream(annotations)
                .anyMatch(annotation -> annotation.annotationType().getAnnotation(RequestMapping.class) != null);
    }

    private void setThreadId() {
        if (threadId.get() == null) {
            Instant now = Instant.now();
            threadId.set(String.format("%d:%d.%d", Thread.currentThread().getId(), now.getEpochSecond(), now.get(ChronoField.MILLI_OF_SECOND)));
        }
    }

    @AllArgsConstructor
    private static class ProceedResult {
        private ProceedingJoinPoint joinPoint;
        private String status;
        private Object ret;
        private Throwable t;
        private long elapsedTime;

        Object getReturnValue() throws Throwable {
            if (Objects.equals(status, "success")) {
                return ret;
            } else {
                throw t;
            }
        }
    }

}
