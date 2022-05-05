package com.akron.CreditLoan.consumer.controller;

import com.akron.CreditLoan.api.consumer.ConsumerAPI;
import com.akron.CreditLoan.api.consumer.model.*;
import com.akron.CreditLoan.api.depository.GatewayRequest;
import com.akron.CreditLoan.common.domain.RestResponse;
import com.akron.CreditLoan.common.util.EncryptUtil;
import com.akron.CreditLoan.consumer.common.SecurityUtil;
import com.akron.CreditLoan.consumer.service.ConsumerService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@Api(value = "用户服务的Controller", tags = "Consumer", description = "用户服务API")
public class ConsumerController implements ConsumerAPI {

    @Value("${depository.url}")
    private String depositoryURL;

    private OkHttpClient okHttpClient=new OkHttpClient().newBuilder().build();

        @Autowired
        private ConsumerService consumerService;


    @Override
    @ApiOperation("用户注册")
    @ApiImplicitParam(name = "consumerRegisterDTO", value = "注册信息", required = true,
            dataType = "AccountRegisterDTO", paramType = "body")
    @PostMapping(value = "/consumers")
    public RestResponse register(@RequestBody ConsumerRegisterDTO consumerRegisterDTO) {
        consumerService.register(consumerRegisterDTO);
        return RestResponse.success();
    }

    @Override
    @ApiOperation("生成开户请求数据")
    @ApiImplicitParam(name = "consumerRequest", value = "开户信息", required = true, dataType = "ConsumerRequest", paramType = "body")
    @PostMapping("/my/consumers")
    public RestResponse<GatewayRequest> createConsumer(@RequestBody ConsumerRequest consumerRequest) {
        consumerRequest.setMobile(SecurityUtil.getUser().getMobile());
        return consumerService.createConsumer(consumerRequest);
    }

    @Override
    @ApiOperation("获取登录用户信息")
    @GetMapping("/l/currConsumer/{mobile}")
    public RestResponse<ConsumerDTO> getCurrConsumer(@PathVariable("mobile") String mobile) {
        ConsumerDTO consumerDTO = consumerService.getByMobile(mobile);
        return RestResponse.success(consumerDTO);
    }

    @Override
    @ApiOperation("获取登录用户信息")
    @GetMapping("/my/consumers")
    public RestResponse<ConsumerDTO> getMyConsumer() {
        ConsumerDTO consumerDTO = consumerService.getByMobile(SecurityUtil.getUser().getMobile());
        return RestResponse.success(consumerDTO);
    }

    @Override
    @ApiOperation("获取借款人用户信息")
    @ApiImplicitParam(name = "id", value = "用户标识", required = true,
            dataType = "Long", paramType = "path")
    @GetMapping("/my/borrowers/{id}")
    public RestResponse<BorrowerDTO> getBorrower(@PathVariable Long id) {
        return RestResponse.success(consumerService.getBorrower(id));
    }

    @Override
    @ApiOperation("获取借款人用户信息-供微服务访问")
    @ApiImplicitParam(name = "id", value = "用户标识", required = true,
            dataType = "Long", paramType = "path")
    @GetMapping("/l/borrowers/{id}")
    public RestResponse<BorrowerDTO> getBorrowerMobile(@PathVariable Long id) {
        return RestResponse.success(consumerService.getBorrower(id));
    }


    @Override
    @ApiOperation("获取用户可用余额")
    @ApiImplicitParam(name = "userNo", value = "用户编码", required = true,
            dataType = "String")
    @GetMapping("/l/balances/{userNo}")
    public RestResponse<BalanceDetailsDTO> getBalance(@PathVariable String userNo) {
        return getBalanceFromDepository(userNo);
    }

    @Override
    @GetMapping("/my/balances")
    public RestResponse<BalanceDetailsDTO> getMyBalance() {
        ConsumerDTO consumerDTO=consumerService.getByMobile(SecurityUtil.getUser().getMobile());
        return getBalanceFromDepository(consumerDTO.getUserNo());
    }

    /**
     远程调用存管系统获取用户余额信息
     @param userNo 用户编码
     @return
     */
    private RestResponse<BalanceDetailsDTO> getBalanceFromDepository(String userNo)
    {
        String url = depositoryURL + "/balance-details/" + userNo;
        BalanceDetailsDTO balanceDetailsDTO;
        Request request = new Request.Builder().url(url).build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                balanceDetailsDTO = JSON.parseObject(responseBody,
                        BalanceDetailsDTO.class);
                return RestResponse.success(balanceDetailsDTO);
            }
        } catch (IOException e) {
            log.warn("调用存管系统{}获取余额失败 ", url, e);
        } return RestResponse.validfail("获取失败");
    }

    @ApiOperation("过网关受保护资源，进行认证拦截测试")
    @ApiImplicitParam(name = "jsonToken", value = "访问令牌", required = true,
            dataType = "String")
    @GetMapping(value = "/m/consumers/test")
    public RestResponse<String> testResources(String jsonToken) {
        return RestResponse.success(EncryptUtil.decodeUTF8StringBase64(jsonToken));
    }


}
