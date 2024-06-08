package com.adam.dark.util;

import com.esotericsoftware.kryo.Kryo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * @author adamboov
 */
@Component
public class DeepCopyUtil {
    private DeepCopyUtil(){}

    private static final ThreadLocal<Kryo> KRYO_THREAD_LOCAL = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        return kryo;
    });

    private static final ForkJoinPool COPY_THREAD_POOL = new ForkJoinPool(16);

    public static <T> T deepCopyByKryo(T target) {
        Callable<T> copyTask = () -> {
            Kryo kryo = KRYO_THREAD_LOCAL.get();
            return kryo.copy(target);
        };
        T copy = null;
        try {
            copy = COPY_THREAD_POOL.submit(copyTask).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return copy;
    }

    public static <T> List<T> deepCopyByKryo(List<T> targetList) {
        Callable<List<T>> copyTask = () -> targetList
                .parallelStream()
                .map(target -> {
                    Kryo kryo = KRYO_THREAD_LOCAL.get();
                    return kryo.copy(target);
                }).collect(Collectors.toList());
        List<T> copyList = null;
        try {
            copyList = COPY_THREAD_POOL.submit(copyTask).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return copyList;
    }

}
