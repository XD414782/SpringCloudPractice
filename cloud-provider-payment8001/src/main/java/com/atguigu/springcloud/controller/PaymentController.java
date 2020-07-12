package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.IPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private IPaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result=paymentService.create(payment);
        log.info("******插入结果："+result);

        if (result>0){
            return new CommonResult(200,"插入success,server_port:"+serverPort,result);
        }else {
            return new CommonResult(444,"插入fail");
        }
    }

    @GetMapping(value = "/payment/get/{id}", produces = { "application/json;charset=UTF-8" })
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment result=paymentService.getPaymentById(id);
        log.info("******插入结果："+result);

        if (result!=null){
            return new CommonResult(200,"查询success,server_port:"+serverPort,result);
        }else {
            return new CommonResult(444,"无对应记录，查询fail");
        }
    }
    @GetMapping(value = "/payment/discovery", produces = { "application/json;charset=UTF-8" })
    public Object discovery(){
        List<String> services=discoveryClient.getServices();
        for(String element:services){
            log.info("*****element:"+element);
        }
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances){
            log.info("serviceId:"+instance.getServiceId()+"\t host:"+instance.getHost()+"\t port:"+instance.getPort()+"\t uri:"+instance.getUri());
        }
        return discoveryClient;
    }
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
    @GetMapping(value = "/payment/lb")
    public String getPayment(){
        return serverPort;
    }
    @GetMapping(value = "/payment/zipkin")
    public String getPaymentZipkin(){
        return "你好哇 我是paymentZipkin";
    }
}
