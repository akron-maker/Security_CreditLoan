package com.akron.CreditLoan.repayment.controller;

import com.akron.CreditLoan.api.repayment.RepaymentApi;
import com.akron.CreditLoan.api.repayment.model.ProjectWithTendersDTO;
import com.akron.CreditLoan.common.domain.RestResponse;
import com.akron.CreditLoan.repayment.service.RepaymentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 还款微服务的Controller
 */
@RestController
public class RepaymentController implements RepaymentApi {

    @Autowired
    private RepaymentService repaymentService;


    @Override
    @ApiOperation("启动还款")
    @ApiImplicitParam(name = "projectWithTendersDTO", value = "通过id获取标的信息",
            required = true, dataType = "ProjectWithTendersDTO",
            paramType = "body")
    @PostMapping("/l/start-repayment")
    public RestResponse<String> startRepayment(@RequestBody ProjectWithTendersDTO projectWithTendersDTO) {
        String result=repaymentService.startRepayment(projectWithTendersDTO);
        return RestResponse.success(result);
    }

    /*@ApiOperation("测试用户还款")
    @GetMapping("/execute-repayment/{date}")
    public void testExecuteRepayment(@PathVariable String date) {
        repaymentService.executeRepayment(date);
    }*/

    @ApiOperation("测试还款短信提醒")
    @GetMapping("/repayment-notify/{date}")
    public void testRepaymentNotify(@PathVariable String date) {
        repaymentService.sendRepaymentNotify(date);
    }

}
