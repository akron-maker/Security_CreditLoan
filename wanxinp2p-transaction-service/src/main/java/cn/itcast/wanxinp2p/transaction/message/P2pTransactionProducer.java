package cn.itcast.wanxinp2p.transaction.message;


import cn.itcast.wanxinp2p.api.repayment.model.ProjectWithTendersDTO;
import cn.itcast.wanxinp2p.transaction.entity.Project;
import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class P2pTransactionProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void updateProjectStatusAndStartRepayment(Project project,
                                                     ProjectWithTendersDTO projectWithTendersDTO) {
        //1.构造消息
        JSONObject object=new JSONObject();
        object.put("project",project);
        object.put("projectWithTendersDTO",projectWithTendersDTO);
        Message<String> msg = MessageBuilder.withPayload(object.toJSONString()).build();

        //2.发送消息
        rocketMQTemplate.sendMessageInTransaction("PID_START_REPAYMENT",
                "TP_START_REPAYMENT", msg, null);
    }
}
