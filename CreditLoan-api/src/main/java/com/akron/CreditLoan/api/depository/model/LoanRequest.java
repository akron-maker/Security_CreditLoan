package com.akron.CreditLoan.api.depository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <P>
 * 标的满标放款信息
 * </p>
 */
@Data
public class LoanRequest {
    /**
     * 放款明细
     */
    private List<LoanDetailRequest> details;
    /**
     * 平台佣金
     */
    private BigDecimal commission;
    /**
     * 标的编码
     */
    private String projectNo;
    /**
     * 请求流水号
     */
    private String requestNo;

    /**
     * 操作对象id
     */
    private Long id;
}
