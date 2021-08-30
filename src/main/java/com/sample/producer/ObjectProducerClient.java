package com.sample.producer;

import com.sample.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectProducerClient {

    @Autowired
    private MqConfig mqConfig;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ObjectProducerBean buildOrderProducer() {
        ObjectProducerBean orderProducerBean = new ObjectProducerBean();
        orderProducerBean.setProperties(mqConfig.getMqPropertie());
        return orderProducerBean;
    }
}
