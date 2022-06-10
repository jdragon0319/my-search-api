package com.example.mysearchapi.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.AopTestUtils;

import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@DisplayName("AOP테스트")
@SpringBootTest(properties = "spring.profiles.active:test")
class CollectLoggingTest {

    @SpyBean
    private CollectLoggingAspect aop;
    @Autowired
    private CollectLoggingTestController controller;

    @DisplayName("AOP적용확인")
    @Test
    void AOP적용확인() {
        AOP적용확인(controller);
    }

    @DisplayName("CollectLogging 이 없는 메서드를 호출하면 aop 메서드를 호출 안한다.")
    @Test
    void AOP미적용메서드미호출() throws Throwable {
        AOP적용확인(controller);

        controller.noApplyAOP();

        BDDMockito.then(aop).should(never()).collectControllerLogging(any());
    }

    @DisplayName("CollectLogging 이 있는 메서드를 호출하면 aop 메서드를 호출한다.")
    @Test
    void AOP적용된api만호출() throws Throwable {
        AOP적용확인(controller);

        controller.callApi(new CollectLoggingTestController.TestRequset("AOP적용"));

        CollectLogging호출횟수확인(1);
        List<ProceedingJoinPoint> allValues = CollectLogging필드조회(1);
        CollectLogging필드값확인(allValues.get(0), "테스트", "Api", "callApi");
    }

    @DisplayName("CollectLogging 이 2번 호출하면 aop 메서드 2번 호출한다.")
    @Test
    void AOP적용된api와service호출() throws Throwable {
        AOP적용확인(controller);

        controller.callApiAndService(new CollectLoggingTestController.TestRequset("AOP적용"));

        CollectLogging호출횟수확인(2);
        List<ProceedingJoinPoint> allValues = CollectLogging필드조회(2);
        CollectLogging필드값확인(allValues.get(0), "테스트", "ApiWithService", "callApiAndService");
        CollectLogging필드값확인(allValues.get(1), "테스트", "Service", "applyAop");
    }

    private void AOP적용확인(CollectLoggingTestController controller) {
        assertThat(AopUtils.isAopProxy(controller)).isTrue();
        assertThat(AopTestUtils.getTargetObject(controller).getClass()).isEqualTo(CollectLoggingTestController.class);
    }

    private List<ProceedingJoinPoint> CollectLogging필드조회(int repeatCallCount) throws Throwable {
        InOrder inOrder = Mockito.inOrder(aop);
        ArgumentCaptor<ProceedingJoinPoint> captor = ArgumentCaptor.forClass(ProceedingJoinPoint.class);
        BDDMockito.then(aop).should(inOrder, times(repeatCallCount)).collectControllerLogging(captor.capture());
        return captor.getAllValues();
    }

    private void CollectLogging호출횟수확인(int repeatCallCount) throws Throwable {
        BDDMockito.then(aop).should(times(repeatCallCount)).collectControllerLogging(any());
    }

    private void CollectLogging필드값확인(ProceedingJoinPoint joinPoint, String menu, String resource, String method) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        CollectLogging annotation = targetMethod.getAnnotation(CollectLogging.class);

        assertThat(annotation.menu()).isEqualTo(menu);
        assertThat(annotation.resource()).isEqualTo(resource);
        assertThat(annotation.method()).isEqualTo(method);
    }

}
