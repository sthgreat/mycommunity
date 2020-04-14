package com.dzkjdx.mycommunity_dbservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectMqConfig {
    public static final String articleAddMq = "addArticle";
    public static final String articleAddExchange = "articleAddExchange";
    public static final String articleAddBindKey = "articleAddBindKey";

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
