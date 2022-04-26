package com.akron.CreditLoan.api.repayment;

import com.akron.CreditLoan.api.repayment.model.ProjectWithTendersDTO;
import com.akron.CreditLoan.common.domain.RestResponse;

public interface RepaymentApi {

    /**
     * 启动还款
     * @param projectWithTendersDTO
     * @return
     */
    public RestResponse<String> startRepayment(ProjectWithTendersDTO
                                                       projectWithTendersDTO);
}
