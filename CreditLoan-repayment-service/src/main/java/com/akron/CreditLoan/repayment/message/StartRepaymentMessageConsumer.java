package com.akron.CreditLoan.repayment.message;

import com.akron.CreditLoan.api.repayment.model.ProjectWithTendersDTO;
import com.akron.CreditLoan.repayment.service.RepaymentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "TP_START_REPAYMENT", consumerGroup = "CID_START_REPAYMENT")
public class StartRepaymentMessageConsumer implements RocketMQListener<String> {

    @Autowired
    private RepaymentService repaymentService;

    @Override
    public void onMessage(String msg) {
        System.out.println("消费消息：" + msg);
        //1.解析消息
        final JSONObject jsonObject = JSON.parseObject(msg);
        ProjectWithTendersDTO projectWithTendersDTO =
                JSONObject.parseObject(jsonObject.getString("projectWithTendersDTO"), ProjectWithTendersDTO.class);

        //2.调用业务层执行本地事务
        repaymentService.startRepayment(projectWithTendersDTO);
    }
}
