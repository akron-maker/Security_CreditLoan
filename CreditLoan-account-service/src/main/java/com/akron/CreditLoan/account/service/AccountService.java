package com.akron.CreditLoan.account.service;

import com.akron.CreditLoan.account.entity.Account;
import com.akron.CreditLoan.api.account.model.AccountDTO;
import com.akron.CreditLoan.api.account.model.AccountLoginDTO;
import com.akron.CreditLoan.api.account.model.AccountRegisterDTO;
import com.akron.CreditLoan.common.domain.RestResponse;
import com.baomidou.mybatisplus.extension.service.IService;


public interface AccountService extends IService<Account> {

    RestResponse getSMSCode(String mobile) ;

    Integer checkMobile(String mobile,String key,String code);

    AccountDTO register(AccountRegisterDTO accountRegisterDTO) ;

    AccountDTO login(AccountLoginDTO accountLoginDTO);
}
