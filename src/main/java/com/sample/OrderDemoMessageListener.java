package com.sample;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDemoMessageListener implements MessageOrderListener {
    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        System.out.println("消费 开始" + message);
        try {
            log.info("消费 body {}", new String(message.getBody()));
            //do something..
            return OrderAction.Success;
        } catch (Exception e) {
            //消费失败，挂起当前队列
            return OrderAction.Suspend;
        }
    }
}
