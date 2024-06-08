package com.adam.dark.base.handle;

import com.adam.dark.base.BaseEntity;
import com.adam.dark.util.NumberUtil;
import com.adam.dark.util.SpringRedisUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author adamboov
 */
@Component
public class MybatisPlusHandler implements MetaObjectHandler {

    @Resource
    private SpringRedisUtil springRedisUtil;

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, BaseEntity.Fields.createTime, LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, BaseEntity.Fields.isDeleted, Boolean.class, false);
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, BaseEntity.Fields.updateTime, LocalDateTime.class, LocalDateTime.now());
    }
}
