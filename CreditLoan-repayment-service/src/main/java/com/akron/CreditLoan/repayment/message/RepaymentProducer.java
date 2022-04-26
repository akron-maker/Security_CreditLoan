package com.akron.CreditLoan.repayment.message;

import com.akron.CreditLoan.api.depository.model.RepaymentRequest;
import com.akron.CreditLoan.repayment.entity.RepaymentPlan;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RepaymentProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void confirmRepayment(RepaymentPlan repaymentPlan, RepaymentRequest repaymentRequest) {
        //1.构造消息
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("repaymentPlan",repaymentPlan);
        jsonObject.put("repaymentRequest",repaymentRequest);

       Message<String> msg = MessageBuilder.withPayload(jsonObject.toJSONString()).build();

        //2.发送消息
        rocketMQTemplate.sendMessageInTransaction("PID_CONFIRM_REPAYMENT",
                "TP_CONFIRM_REPAYMENT", msg, null);
    }
}
