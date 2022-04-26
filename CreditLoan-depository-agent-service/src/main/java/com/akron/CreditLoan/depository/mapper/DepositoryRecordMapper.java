package com.akron.CreditLoan.depository.mapper;

import com.akron.CreditLoan.depository.entity.DepositoryRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
   * 存管交易记录表 Mapper 接口
   */
@Mapper
public interface DepositoryRecordMapper extends BaseMapper<DepositoryRecord> {
}
