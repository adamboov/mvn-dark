local key = tostring(KEYS[1]);
local count = tonumber(KEYS[2]);
local max_value = tonumber(KEYS[3])

local num_redis = redis.call("incr", key);

local init_num = 0
if (tonumber(num_redis) >= max_value) then
    num_redis = init_num
    redis.call("set", key, num_redis);
end
-- 计算数字的位数
local function dight_num(num)
    if math.floor(num) ~= num or num < 0 then
        return -1;
    elseif 0 == num then
        return 1;
    else
        local tmp_dight = 0;
        while num > 0 do
            num = math.floor(num / 10);
            tmp_dight = tmp_dight + 1;
        end
        return tmp_dight;
    end
end
-- 在整数数字前面加0
-- dest_dight 标识最终生成位数，例如 full_zero_front_num(5, 1) 计算后是00001
local function full_zero_front_num(dest_dight, num)
    local num_dight = dight_num(num);
    if -1 == num_dight then
        return -1;
    elseif dest_dight <= num_dight then
        return tostring(num);
    else
        local str_e = ""
        for _ = 1, dest_dight - num_dight do
            str_e = str_e .. "0";
        end
        return str_e .. tostring(num);
    end
end
local idStr = full_zero_front_num(count, num_redis);
return idStr;
