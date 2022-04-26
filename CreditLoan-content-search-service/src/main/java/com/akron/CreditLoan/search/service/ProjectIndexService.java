package com.akron.CreditLoan.search.service;

import com.akron.CreditLoan.api.search.model.ProjectQueryParamsDTO;
import com.akron.CreditLoan.api.transaction.model.ProjectDTO;
import com.akron.CreditLoan.common.domain.PageVO;

/**
 * 标的检索业务层接口
 */
public interface ProjectIndexService {
    PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO
                                                 projectQueryParamsDTO,
                                         Integer pageNo, Integer pageSize,
                                         String sortBy, String order);
}
