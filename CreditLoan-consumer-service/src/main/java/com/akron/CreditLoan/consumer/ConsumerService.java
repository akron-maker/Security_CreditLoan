package com.akron.CreditLoan.consumer;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = MongoAutoConfiguration.class,scanBasePackages = {"org.dromara.hmily","com.akron.CreditLoan.consumer"})
@EnableDiscoveryClient
@MapperScan("com.akron.CreditLoan.consumer.mapper")
@EnableFeignClients(basePackages = {"com.akron.CreditLoan.consumer.agent"})
public class ConsumerService {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerService.class, args);
    }
}