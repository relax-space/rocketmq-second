package com.sample.producer;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.sample.MqConfig;
import com.sample.ObjectUtil;
import com.sample.Person;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ObjectController {
    @GetMapping("/ping")
    public String index() {
        return "pong";
    }


    //顺序消息的Producer 已经注册到了spring容器中，后面需要使用时可以直接注入到其它类中
    @Autowired
    private ObjectProducerBean orderProducer;

    @Autowired
    private MqConfig mqConfig;

    @GetMapping("/mq/obj")
    public void mq(int count) {
        String shardingKey = "OrderedKey";
        //循环发送消息
        for (int i = 0; i < count; i++) {
            try {
                Person p = new Person();
                p.setName(String.format("序列化对象 %s", i));
                Message msg = new Message( //
                        // Message所属的Topic
                        mqConfig.getOrderTopic(),
                        // Message Tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在MQ服务器过滤
                        mqConfig.getOrderTag(),
                        // Message Body 可以是任何二进制形式的数据， MQ不做任何干预
                        // 需要Producer与Consumer协商好一致的序列化和反序列化方式
                        ObjectUtil.objectSerialize(p));
                // 设置代表消息的业务关键属性，请尽可能全局唯一
                // 以方便您在无法正常收到消息情况下，可通过MQ 控制台查询消息并补发
                // 注意：不设置也不会影响消息正常收发
                msg.setKey("ORDERID_100");
                // 发送消息，只要不抛异常就是成功

                SendResult sendResult = orderProducer.send(msg, shardingKey);
                assert sendResult != null;
                log.info("发送 成功 {}", sendResult.getTopic());
            } catch (Exception e) {
                log.error("发送 失败 {}", ExceptionUtils.getStackTrace(e));
                //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
            }
        }
    }
}
