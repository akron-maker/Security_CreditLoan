package com.akron.CreditLoan.api.repayment.model;

import com.akron.CreditLoan.api.transaction.model.ProjectDTO;
import com.akron.CreditLoan.api.transaction.model.TenderDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * <P>
 * 标的还款信息
 * </p>
 */
@Data
public class ProjectWithTendersDTO {
    /**
     * 标的信息
     */
    private ProjectDTO project;
    /**
     * 标的对应的所有投标记录
     */
    private List<TenderDTO> tenders;

    /**
     * 投资人让出利率 ( 投资人让利 )
     */
    private BigDecimal commissionInvestorAnnualRate;

    /**
     * 借款人给平台的利率 ( 借款人让利 )
     */
    private BigDecimal commissionBorrowerAnnualRate;

}
