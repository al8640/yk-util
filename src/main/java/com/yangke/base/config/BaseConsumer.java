package com.yangke.base.config;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author ke.yang1
 * @description
 * @date 2020/12/7 10:58 上午
 */
@Component
@Slf4j
public class BaseConsumer {
    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * @param message
     */
    @RabbitListener(
            bindings = {@QueueBinding(value = @Queue(value = "${push.queue}", durable = "true"),
                    exchange = @Exchange(value = "${push.exchange}", type = "topic"),
                    key = "${push.routingkey}"+"${runtime.env}")},concurrency = "20")
    @RabbitHandler
    public void pushMessageConsume(org.springframework.amqp.core.Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("Rabbitmq  Consumer pushMessageConsume:[{}]", msg);
        if (StringUtils.isEmpty(msg)) {
            return;
        }
        String nativePushDTO = JSON.parseObject(msg, String.class);
    }
}
