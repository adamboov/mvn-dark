package com.adam.dark.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author VAIO-adam
 * 1.标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中
 * 2.标注为@MappedSuperclass的类不能再标注@Entity或@Table注解，也无需实现序列化接口。
 */
@ApiModel(description = "所有表的基础类  创建人 创建时间")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1797541363941118841L;

    @ApiModelProperty("主键id 默认自动递增")
    @TableId

    @Id
    @Comment("id 主键")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ApiModelProperty("创建人")
    @Column(nullable = false)
    String createBy;

    @Comment("创建人id")
    Long createById;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)

    @Column(nullable = false, updatable = false)
    LocalDateTime createTime;

    @ApiModelProperty("更新人")
    String updateBy;

    @Comment("更新人id")
    Long updateById;

    @ApiModelProperty("更新时间")

    @TableField(fill = FieldFill.UPDATE)

    @Comment("更新时间")
    LocalDateTime updateTime;

    @Comment("是否删除")
    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    Boolean isDeleted;

    @Comment("删除时间")
    LocalDateTime deletedTime;

    /**
     * 方法用于在插入实体到数据库之前执行
     */
    @PrePersist
    protected void prePersist() {
        this.createTime = LocalDateTime.now();
        // 删除时  是否删除字段为true 删除时间为当前时间
        if (Boolean.TRUE.equals(this.isDeleted)) {
            this.deletedTime = this.createTime;
        } else {
            //  有可能为null    故给false值
            this.isDeleted = false;
            this.deletedTime = null;
        }
    }

    /**
     * 方法用于在更新实体到数据库之前执行
     */
    @PreUpdate
    protected void preUpdate() {
        this.updateTime = LocalDateTime.now();
        if (Boolean.TRUE.equals(this.isDeleted)) {
            this.deletedTime = this.updateTime;
        } else {
            this.deletedTime = null;
        }
    }

    /**
     * 方法用于在从数据库加载实体数据后
     */
    @PostLoad
    protected void postLoad() {
        if (Boolean.TRUE.equals(this.isDeleted)) {
            this.deletedTime = LocalDateTime.now();
        } else {
            this.deletedTime = null;
        }
    }

    /**
     * 在删除实体之前触发，可以在此方法中执行相关的逻辑
     */
    @PreRemove
    protected void preRemove() {
    }

    /**
     * 在成功插入实体到数据库之后触发，可以在此方法中执行相关的逻辑
     */
    @PostPersist
    protected void postPersist() {
    }

    /**
     * 在成功更新实体到数据库之后触发，可以在此方法中执行相关的逻辑
     */
    @PostUpdate
    protected void postUpdate() {
    }

    /**
     * 在成功删除实体之后触发，可以在此方法中执行相关的逻辑
     */
    @PostRemove
    protected void postRemove() {
    }

}
