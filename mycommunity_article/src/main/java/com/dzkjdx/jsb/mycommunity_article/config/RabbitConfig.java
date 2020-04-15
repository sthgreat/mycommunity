package com.dzkjdx.jsb.mycommunity_article.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String articleAddMq = "addArticle";
    public static final String articleAddExchange = "articleAddExchange";
    public static final String articleAddBindKey = "articleAddBindKey";

}
