package com.akron.CreditLoan.consumer.service;

import com.akron.CreditLoan.api.consumer.model.BorrowerDTO;
import com.akron.CreditLoan.api.consumer.model.ConsumerDTO;
import com.akron.CreditLoan.api.consumer.model.ConsumerRegisterDTO;
import com.akron.CreditLoan.api.consumer.model.ConsumerRequest;
import com.akron.CreditLoan.api.depository.GatewayRequest;
import com.akron.CreditLoan.api.depository.model.DepositoryConsumerResponse;
import com.akron.CreditLoan.common.domain.RestResponse;
import com.akron.CreditLoan.consumer.entity.Consumer;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ConsumerService extends IService<Consumer> {
    /**
     * 检测用户是否存在
     * @param mobile
     * @return
     */
    Integer checkMobile(String mobile);
    /**
     * 用户注册
     * @param consumerRegisterDTO
     * @return
     */
    void register(ConsumerRegisterDTO consumerRegisterDTO);

    /**
    生成开户数据
    @param consumerRequest
    @return
    */
    RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest);

    /**
     * 更新开户结果
     * @param response
     * @return
     */
    Boolean modifyResult(DepositoryConsumerResponse response);

    /**
     * 通过手机号获取当前用户信息
     * @param mobile
     * @return
     */
    ConsumerDTO getByMobile(String mobile);

    /**
        * 获取借款人基本信息
        * @param id
        * @return
     */
    BorrowerDTO getBorrower(Long id);
}