package com.adam.dark.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用RedisTemplate封装的工具类
 *
 * @author VAIO-adam
 */
@Component
public final class SpringRedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    public RedisTemplate redisTemplate() {
        return redisTemplate;
    }
    public StringRedisTemplate stringRedisTemplate() {
        return stringRedisTemplate;
    }

    /**
     * 从 1  开始自增
     *
     * @param key key值
     * @return 返回当前自增id值
     */
    public Long autoIncrementId(String key) {
        return redisTemplate().opsForValue().increment(key);
    }

    public String runLuaScript(String luaFileName, List<String> keyList) {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/" + luaFileName)));
        redisScript.setResultType(String.class);
        return stringRedisTemplate.execute(redisScript, keyList);
    }

    public Long runLuaScript(List<String> keyList) {
        return Long.parseLong(this.runLuaScript("darkId.lua", keyList));
    }


    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间（秒）
     * @return 成功与否
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param key 获取过期时间键
     * @return 时间（秒） -1 表示永久有效 -2 表示不存在
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setList(String key, List list) {
        try {
            redisTemplate.opsForList().rightPushAll(key, list.toArray());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List getList(String key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    public boolean setSetFromList(String key, List list) {
        try {
            redisTemplate.opsForSet().add(key, list.toArray());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Set<Object> getSet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

    public Set<Object> getSetToList(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

}
