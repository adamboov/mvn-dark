package com.adam.dark.base;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author adamboov
 */
@ApiModel(description = "所有表的基础类  创建人 创建时间")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseVO implements Serializable {

    private static final long serialVersionUID = -3035220033672115559L;

    @ApiModelProperty("主键")
    Long id;

    @ApiModelProperty("创建人")
    String createBy;

    @Comment("创建人id")
    Long createById;

    @ApiModelProperty("创建时间")
    LocalDateTime createTime;

    @ApiModelProperty("更新人")
    String updateBy;

    @Comment("更新人id")
    Long updateById;

    @ApiModelProperty("更新时间")
    LocalDateTime updateTime;

    @ApiModelProperty("是否删除")
    Boolean isDeleted;

}
