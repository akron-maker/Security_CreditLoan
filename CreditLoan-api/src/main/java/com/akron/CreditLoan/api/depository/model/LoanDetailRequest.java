package com.akron.CreditLoan.api.depository.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <P>
 * 放款明细请求信息
 * </p>
 */
@Data
public class LoanDetailRequest {
    /**
     * 放款金额
     */
    private BigDecimal amount;
    /**
     * 预处理业务流水号
     */
    private String preRequestNo;
}
