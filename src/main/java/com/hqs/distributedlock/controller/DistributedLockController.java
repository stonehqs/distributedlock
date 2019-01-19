package com.hqs.distributedlock.controller;

import com.hqs.distributedlock.annotation.DistriLimitAnno;
import com.hqs.distributedlock.lock.DistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class DistributedLockController {

    @Autowired
    private DistributedLock lock;

    @PostMapping("/distributedLock")
    @ResponseBody
    public String distributedLock(String key, String uuid, String secondsToLock, String userId) throws Exception{
//        String uuid = UUID.randomUUID().toString();
        Boolean locked = false;
        try {
            locked = lock.distributedLock(key, uuid, secondsToLock);
            if(locked) {
                log.info("userId:{} is locked - uuid:{}", userId, uuid);
                log.info("do business logic");
                TimeUnit.MICROSECONDS.sleep(3000);
            } else {
                log.info("userId:{} is not locked - uuid:{}", userId, uuid);
            }
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            if(locked) {
                lock.distributedUnlock(key, uuid);
            }
        }

        return "ok";
    }

    @PostMapping("/distributedLimit")
    @ResponseBody
    @DistriLimitAnno(limitKey="limit", limit = 10)
    public String distributedLimit(String userId) {
        log.info(userId);
        return "ok";
    }

}
