package com.sample.consumer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import com.sample.ObjectUtil;
import com.sample.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ObjectMessageListener implements MessageOrderListener {
    @Override
    public OrderAction consume(final Message message, final ConsumeOrderContext context) {
        log.info("消费 开始 {}", message);
        try {
            Person person = (Person) ObjectUtil.objectDeserialize(message.getBody());
            log.info("消费 成功 {}", person);
            return OrderAction.Success;
        } catch (Exception e) {
            //消费失败，挂起当前队列
            log.info("消费 失败 {}", ExceptionUtils.getStackTrace(e));
            return OrderAction.Success;
        }
    }
}
