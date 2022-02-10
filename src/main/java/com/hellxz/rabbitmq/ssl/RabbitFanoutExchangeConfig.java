package com.hellxz.rabbitmq.ssl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.DefaultSaslConfig;


@Configuration
public class RabbitFanoutExchangeConfig {
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    public static final String FANOUT_QUEUE1 = "fanout.queue1";

    @Bean(name = FANOUT_EXCHANGE)
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE, true, false);
    }


    @Bean(name = FANOUT_QUEUE1)
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE1, true, false, false);
    }
    
    @Bean
    public Binding bindingSimpleQueue1(@Qualifier(FANOUT_QUEUE1) Queue fanoutQueue1,
                                       @Qualifier(FANOUT_EXCHANGE) FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
    
    @Autowired
    RabbitProperties rabbitProperties;
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;
    
    /**
     * 解决安全扫描 AMQP明文登录漏洞 仅当rabbitmq启用ssl时并且配置证书时，显式设置EXTERNAL认证机制<br/>
     * EXTERNAL认证机制使用X509认证方式，服务端读取客户端证书中的CN作为登录名称，同时忽略密码
     */
    @PostConstruct
    public void rabbitmqSslExternalPostConstruct() {
        boolean rabbitSslEnabled = BooleanUtils.toBoolean(rabbitProperties.getSsl().getEnabled());
        boolean rabbitSslKeyStoreExists = rabbitProperties.getSsl().getKeyStore() != null;
        if (rabbitSslEnabled && rabbitSslKeyStoreExists) {
            cachingConnectionFactory.getRabbitConnectionFactory().setSaslConfig(DefaultSaslConfig.EXTERNAL);
        }
    }
}
