package com.example.mysearchapi.common.logging;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectLoggingTestController {

    private CollectLoggingTestService service;

    public CollectLoggingTestController(CollectLoggingTestService service) {
        this.service = service;
    }

    @GetMapping("/aoptestapi/callApiAndService")
    @CollectLogging(menu = "테스트", resource = "ApiWithService", method = "callApiAndService")
    public String callApiAndService(TestRequset test) {
        service.applyAop(test);
        System.out.println("controller test");
        return "test";
    }

    @GetMapping("/aoptestapi/callApi")
    @CollectLogging(menu = "테스트", resource = "Api", method = "callApi")
    public String callApi(TestRequset test) {
        System.out.println("controller test");
        return "test";
    }

    public String noApplyAOP() {
        System.out.println("no aop");
        return "no";
    }

    public record TestRequset(String text) {
    }

}
