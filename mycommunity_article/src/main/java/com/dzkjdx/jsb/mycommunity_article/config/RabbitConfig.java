package com.dzkjdx.jsb.mycommunity_article.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RabbitConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String articleAddMq = "addArticle";
    public static final String articleAddExchange = "articleAddExchange";
    public static final String articleAddBindKey = "articleAddBindKey";
}
