package com.example.mysearchapi.common.logging;

import org.springframework.stereotype.Service;

@Service
public class CollectLoggingTestService {

    @CollectLogging(menu = "테스트", resource = "Service", method = "applyAop")
    public void applyAop(CollectLoggingTestController.TestRequset test) {
        System.out.println(test.text());
    }

    public void nonApplyAop() {
        System.out.println("AOP 미적용");
    }

}
