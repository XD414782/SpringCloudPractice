package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

public interface IPaymentService {
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}
