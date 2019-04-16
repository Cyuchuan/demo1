package com.cyc.demo1.random;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author chenyuchuan
 */
public class RandomUtil {

    private RandomUtil() {

    }

    /**
     * 生成随机数字，在最小值min和bound之间，不包含bound
     * 
     * @param min
     *            最小值
     * @param bound
     *            上限值（排除）
     * @return 随机值
     */
    public static long nextLongBetween(long min, long bound) {
        return ThreadLocalRandom.current().nextLong(min, bound);
    }

    /**
     * 获得随机数字[0,上限值）
     * 
     * @param bound
     *            上限值
     * @return 随机数字
     */
    public static long nextLong(long bound) {
        return ThreadLocalRandom.current().nextLong(bound);
    }

    /**
     * 获得随机数字
     * 
     * @return 随机数字
     */
    public static long nextLong() {
        return ThreadLocalRandom.current().nextLong();
    }

    /**
     * 生成随机数字，在最小值min和最大值max之间，不包含最大值max
     *
     * @param min
     *            最小值
     * @param bound
     *            上限值（排除）
     * @return 随机值
     */
    public static long nextInt(int min, int bound) {
        return ThreadLocalRandom.current().nextInt(min, bound);
    }

    /**
     * 获得随机数字[0,上限值）
     * 
     * @param bound
     *            上限值
     * @return 返回随机值
     */
    public static long nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }

    /**
     * 生成整型随机值
     * 
     * @return 随机值
     */
    public static long nextInt() {
        return ThreadLocalRandom.current().nextInt();
    }

    public static int nextIntWithoutThreadLocal(int min, int bound) {
        Random random = new Random();
        return random.nextInt(bound - min) + min;

    }
}
