--  对传入的值进行拼接
local key = KEYS[1]
local max_value = KEYS[2]
--  获取 缓存值
local current = redis.call('get', key)
--  如果没有获取就设置 或到达最大值   当前基数为0
local result = 0
if not current or tonumber(current) == tonumber(max_value) then
    current = result
else
    current = tonumber(current) + 1
end
-- 加1 存redis
redis.call('set', key, current)
-- 返回当前取出的值
return tostring(current)