package com.akron.CreditLoan.uaa.agent;

import com.akron.CreditLoan.api.account.model.AccountDTO;
import com.akron.CreditLoan.api.account.model.AccountLoginDTO;
import com.akron.CreditLoan.common.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "account-service")
public interface AccountApiAgent {

    @PostMapping(value = "/account/l/accounts/session")
    public RestResponse<AccountDTO> login(@RequestBody AccountLoginDTO accountLoginDTO);
}
