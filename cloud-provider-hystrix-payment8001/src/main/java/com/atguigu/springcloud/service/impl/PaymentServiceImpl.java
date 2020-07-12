package com.atguigu.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.atguigu.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
//    服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if(id < 0){
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = IdUtil.simpleUUID();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }


//    服务降级
    @Override
    public String paymentInfo_OK(Integer id) {
        return "线程池  "+Thread.currentThread().getName()+"  paymentInfo_OK,id:  "+id+"\tO(∩_∩)O哈哈~";
    }

    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="6000")
    })
    public String paymentInfo_TimeOut(Integer id) {
        int time=5;
        try{
            TimeUnit.SECONDS.sleep(time);
            //int age=10/0;     //只要是服务不可用了 马上做服务降级
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池  "+Thread.currentThread().getName()+"  paymentInfo_TimeOut,id:  "+id+"\tO(∩_∩)O哈哈~"+"  耗时："+time+"秒";
    }

    public String paymentInfo_TimeOutHandler(Integer id) {
        return "线程池  "+Thread.currentThread().getName()+"  paymentInfo_TimeOutHandler,id:  "+id+"\t/(ㄒoㄒ)/~~";
    }
}
