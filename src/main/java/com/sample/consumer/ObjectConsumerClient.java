package com.sample.consumer;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.sample.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//项目中加上 @Configuration 注解，这样服务启动时consumer也启动了
@Configuration
public class ObjectConsumerClient {

    @Autowired
    private MqConfig mqConfig;

    @Autowired
    private ObjectMessageListener messageListener;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ObjectConsumerBean buildOrderConsumer() {
        ObjectConsumerBean orderConsumerBean = new ObjectConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getOrderGroupId());
        orderConsumerBean.setProperties(properties);
        //订阅关系
        Map<Subscription, MessageOrderListener> subscriptionTable = new HashMap<>();
        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getOrderTopic());
        subscription.setExpression(mqConfig.getOrderTag());
        subscriptionTable.put(subscription, messageListener);
        //订阅多个topic如上面设置

        orderConsumerBean.setSubscriptionTable(subscriptionTable);
        return orderConsumerBean;
    }

}
