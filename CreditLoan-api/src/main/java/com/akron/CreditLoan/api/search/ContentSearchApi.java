package com.akron.CreditLoan.api.search;

import com.akron.CreditLoan.api.search.model.ProjectQueryParamsDTO;
import com.akron.CreditLoan.api.transaction.model.ProjectDTO;
import com.akron.CreditLoan.common.domain.PageVO;
import com.akron.CreditLoan.common.domain.RestResponse;

public interface ContentSearchApi {
    /**
     * 检索标的
     * @param projectQueryParamsDTO
     * @return
     */
    RestResponse<PageVO<ProjectDTO>> queryProjectIndex(
            ProjectQueryParamsDTO projectQueryParamsDTO,
            Integer pageNo,Integer pageSize,String sortBy,String order);
}
