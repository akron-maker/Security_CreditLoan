package com.akron.CreditLoan.transaction.service;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <P>
 * 本类用于获取配置文件中的配置, 封装成service方便调用
 * </p>
 */
@Service
@EnableApolloConfig
public class ConfigService {
    @ApolloConfig
    private Config config;

    /**
     * 获取流标时间, 单位天
     */
    public Integer getMiscarryDays() {
        return Integer.parseInt(config.getProperty("miscarry.days", "15"));
    }

    /**
     * 借款人佣金
     */
    public BigDecimal getCommissionBorrowerAnnualRate() {
        return new BigDecimal(config.getProperty("commission.borrower.annual.rate", "0.12"));
    }


    /**
     * 投资人佣金
     */
    public BigDecimal getCommissionInvestorAnnualRate() {
        return new BigDecimal(config.getProperty("commission.investor.annual.rate", "0.10"));
    }


    /**
     * 年化利率(平台佣金，利差)
     *
     * @return
     */
    public BigDecimal getCommissionAnnualRate() {
        return getCommissionBorrowerAnnualRate().add(getCommissionInvestorAnnualRate());
    }

    /**
     * 年化利率(借款人)
     *
     * @return
     */
    public BigDecimal getBorrowerAnnualRate() {
        return new BigDecimal(config.getProperty("borrower.annual.rate", "0.15"));
    }

    /**
     * !!!!年化利率(投资人) = 借款人利率0.15 - 平台佣金( 借款人佣金  +  投资人佣金 )!!!!!
     *
     * @return
     */
    public BigDecimal getAnnualRate() {
        return getBorrowerAnnualRate().subtract(getCommissionAnnualRate());
    }

    /**
     * 最小投标金额
     *
     * @return
     */
    public BigDecimal getMiniInvestmentAmount() {
        // 如果配置文件中没有获取到, 这里使用默认值: 100.0, 有则使用配置文件中的
        return new BigDecimal(config.getProperty("mini.investment.amount", "100.0"));
    }


}
