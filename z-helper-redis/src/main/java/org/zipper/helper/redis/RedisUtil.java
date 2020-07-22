package org.zipper.helper.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.zipper.helper.exception.HelperException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    public static void init(RedisTemplate<String, Object> redisTemplate) {
        if (RedisUtil.redisTemplate != null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "请勿重复初始化RedisTemplate实例");
        }
        RedisUtil.redisTemplate = redisTemplate;
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return boolean
     */
    public static Boolean expire(String key, long time) {

        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        if (time <= 0) {
            throw HelperException.newException(RedisError.EXPIRE_TIME_ERROR, String.format("过期时间[%s]不合法", time));
        }
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static Long getExpire(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static Boolean hasKey(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public static void del(String... key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }

        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    //============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public static void set(String key, Object value, long time) {

        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }

        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public static Long incr(String key, long delta) {

        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }

        if (delta < 0) {
            throw HelperException.newException(RedisError.PARAMETER_ERROR, "递增因子必须大于0");
        }

        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public static Long decr(String key, long delta) {

        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }

        if (delta < 0) {
            throw HelperException.newException(RedisError.PARAMETER_ERROR, "递减因子必须大于0");
        }

        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key, String item) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmget(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static void hmset(String key, Map<String, Object> map) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public static void hmset(String key, Map<String, Object> map, long time) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForHash().putAll(key, map);

        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public static void hset(String key, String item, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForHash().put(key, item, value);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public static void hset(String key, String item, Object value, long time) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForHash().put(key, item, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public static Double hincr(String key, String item, double by) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public static Double hdecr(String key, String item, double by) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public static Set<Object> sGet(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static Boolean sHasKey(String key, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForSet().isMember(key, value);
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static Long sSet(String key, Object... values) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForSet().add(key, values);

    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static Long sSetAndTime(String key, long time, Object... values) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        Long count = redisTemplate.opsForSet().add(key, values);
        if (time > 0) {
            expire(key, time);
        }
        return count;
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static Long sGetSetSize(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static Long setRemove(String key, Object... values) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForSet().remove(key, values);

    }
    //===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static Long lGetListSize(String key) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static Object lGetIndex(String key, long index) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static Long lSet(String key, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static Long lSet(String key, Object value, long time) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        Long result = redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return result;
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static Long lSet(String key, List<Object> value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static Long lSet(String key, List<Object> value, long time) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        Long result = redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
        return result;
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static void lUpdateIndex(String key, long index, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static Long lRemove(String key, long count, Object value) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }
        return redisTemplate.opsForList().remove(key, count, value);
    }


    /**
     * 添加锁
     *
     * @param key  键
     * @param time 过期时间
     * @return 加锁结果
     */
    public static boolean addLock(String key, long time) {
        if (redisTemplate == null) {
            throw HelperException.newException(RedisError.INIT_ERROR, "未初始化RedisTemplate实例");
        }

//        Boolean result = redisTemplate.opsForValue().set(key, key, time, TimeUnit.SECONDS);

//        if (result == null) {
//            throw HelperException.newException(RedisError.SET_ERROR, "新增锁失败");
//        }
//
//        return result;
        return true;
    }

}