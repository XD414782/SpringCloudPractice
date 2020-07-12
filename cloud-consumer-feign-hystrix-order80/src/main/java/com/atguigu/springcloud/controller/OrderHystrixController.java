package com.atguigu.springcloud.controller;

        import com.atguigu.springcloud.service.PaymentHystrixService;
        import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
        import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
        import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
        import lombok.extern.slf4j.Slf4j;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RestController;

        import javax.annotation.Resource;
        import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id")Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    public String payment_global_fallbackMethod(){
        return "global处理异常信息，请稍后再试";
    }
    @HystrixCommand()
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

//    容易使代码膨胀↓
//    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="1500")
//    })
//    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
//        return paymentHystrixService.paymentInfo_TimeOut(id);
//    }
//    public String paymentInfo_TimeOutHandler(Integer id) {
//        return "我是消费者80，对方支付系统繁忙，请10s之后再尝试/(ㄒoㄒ)/~~";
//    }

}
