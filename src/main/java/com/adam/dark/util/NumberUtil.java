package com.adam.dark.util;

import cn.hutool.core.date.DatePattern;
import com.adam.dark.base.consts.NumConst;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @author VAIO-adam
 * 随机数工具类
 */
public class NumberUtil {

    private static volatile long count = 0;

    /**
     * 返回 0-99999 直接的自增数
     *
     * @return 返回 0-99999 直接的自增数
     */
    private static synchronized long getCount() {
        if (count == NumConst.MIN_6_NUM) {
            count = 0;
        }
        return count++;
    }


    /**
     * 生成指定位随机数字
     * 若没指定就是4位
     */
    public static String genRandom(int randomLength) {
        if (randomLength == 0) {
            randomLength = 4;
        }
        Random random = new Random();
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < randomLength; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    /**
     * 生成六位随机数字
     */
    public static String genSixRandom() {
        return genRandom(6);
    }

    /**
     * @param formatCount 需要的位数
     * @param number      要格式化的对象数字
     * @return formatNumber(4, 1) == 0001   formatNumber(4,10001) == 0001
     */
    public static String formatNumber(int formatCount, Number number) {
        //格式化拼接      5   :   00000
        //              6   :   000000
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < formatCount; i++) {
            stringBuffer.append("0");
        }
        DecimalFormat decimalFormat = new DecimalFormat(stringBuffer.toString());
        //配置最大位数  超出部分截取前面部分 不足补0
        decimalFormat.setMaximumIntegerDigits(formatCount);
        return decimalFormat.format(number);
    }

    /**
     * 根据当前时间生成 yyyyMMddHHmmss
     *
     * @return yyyyMMddHHmmss字符串
     */
    public static String dateTimeNumber() {
        return DateTimeFormatter.ofPattern(DatePattern.PURE_DATETIME_PATTERN).format(LocalDateTime.now());
    }

    /**
     * 根据当前时间生成 yyyyMMddHHmmss
     *
     * @return yyyyMMddHHmmss字符串
     */
    public static Long dateTimeNumberLong() {
        return Long.parseLong(dateTimeNumber());
    }

    /**
     * 前14位 是当前时间的年月日时分秒  后5位 是  00000-99999 之间的递增数
     * 单机锁 版本
     *
     * @return 20220202020202 + 00000 字符串
     */
    public static String orderNumber() {
        String date = dateTimeNumber();
        String number = formatNumber(5, getCount());
        return date + number;
    }

    /**
     * 上面方法的重载
     *
     * @param number    数字
     * @return  返回当前时间与后面数字的拼接
     */
    public static String orderNumber(Long number) {
        return dateTimeNumber() + formatNumber(5, number);
    }

    /**
     * 前14位 是当前时间的年月日时分秒  后5位 是  00000-99999 之间的递增数
     *
     * @return long  类型
     */
    public static long orderNumberLong() {
        return Long.parseLong(orderNumber());
    }
    /**
     * 前14位 是当前时间的年月日时分秒  后5位 是  00000-99999 之间的递增数
     *
     * @return long  类型
     */
    public static long orderNumberLong(Long number) {
        return Long.parseLong(orderNumber(number));
    }

}
