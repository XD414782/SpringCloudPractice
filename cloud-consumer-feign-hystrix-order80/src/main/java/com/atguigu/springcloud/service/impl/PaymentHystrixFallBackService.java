package com.atguigu.springcloud.service.impl;

import com.atguigu.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentHystrixFallBackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "/(ㄒoㄒ)/~~PaymentHystrixFallBackService-----fallback----paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "/(ㄒoㄒ)/~~PaymentHystrixFallBackService-----fallback----paymentInfo_TimeOut";
    }
}
