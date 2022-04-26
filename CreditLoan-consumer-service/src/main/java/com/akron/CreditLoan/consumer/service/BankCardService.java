package com.akron.CreditLoan.consumer.service;

import com.akron.CreditLoan.api.consumer.model.BankCardDTO;
import com.akron.CreditLoan.consumer.entity.BankCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户绑定银行卡信息 服务类
 */
public interface BankCardService extends IService<BankCard> {
    /**
     * 获取银行卡信息
     * @param consumerId 用户id
     * @return
     */
    BankCardDTO getByConsumerId(Long consumerId);
    /**
     * 获取银行卡信息
     * @param cardNumber 卡号
     * @return
     */
    BankCardDTO getByCardNumber(String cardNumber);
}
