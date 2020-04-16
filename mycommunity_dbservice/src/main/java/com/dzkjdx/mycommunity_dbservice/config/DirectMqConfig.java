package com.dzkjdx.mycommunity_dbservice.config;

import com.dzkjdx.mycommunity_dbservice.receiver.addArticleReceiver;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectMqConfig {
    public static final String articleAddMq = "addArticle";
    public static final String articleAddExchange = "articleAddExchange";
    public static final String articleAddBindKey = "articleAddBindKey";

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private addArticleReceiver addArticleReceiver;

    @Bean
    public Queue articleAddMq(){
        return new Queue(articleAddMq,true);
    }

    @Bean
    public DirectExchange articleAddExchange(){
        return new DirectExchange(articleAddExchange);
    }

    @Bean
    public Binding articleAddBind(){
        return BindingBuilder.bind(articleAddMq()).to(articleAddExchange()).with(articleAddBindKey);
    }

}
