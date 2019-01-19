package com.hqs.distributedlock.limit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author huangqingshi
 * @Date 2019-01-17
 */
@Slf4j
@Component
public class DistributedLimit {

    //注意RedisTemplate用的String,String，后续所有用到的key和value都是String的
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    RedisScript<Long> limitScript;

    public Boolean distributedLimit(String key, String limit) {
        Long id = 0L;

        try {
            id = redisTemplate.execute(limitScript, Collections.singletonList(key),
                    limit);
            log.info("id:{}", id);
        } catch (Exception e) {
            log.error("error", e);
        }

        if(id == 0L) {
            return false;
        } else {
            return true;
        }
    }

}
