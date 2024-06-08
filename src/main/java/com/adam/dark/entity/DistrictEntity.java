package com.adam.dark.entity;

import com.adam.dark.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author adamboov
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("bs_district")
@Table(appliesTo = "bs_district", comment = "行政区域表")
@Entity(name = "bs_district")
public class DistrictEntity extends BaseEntity {

    private static final long serialVersionUID = 8617593890270545098L;

    @Comment("名称")
    @Column(nullable = false)
    String name;

    @Comment("父级id")
    Long parentId;

    @Comment("id 按顺序来的 逗号拼接的字符串 ")
    String currentIdSort;

    @Comment("名称 按顺序来的 逗号拼接的字符串")
    String currentNameSort;

    @Comment("名称 集合")
    String currentNameList;

}
