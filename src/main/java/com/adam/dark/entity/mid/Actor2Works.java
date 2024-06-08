package com.adam.dark.entity.mid;

import com.adam.dark.base.consts.MidTableConst;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @author adamboov
 */
@Data
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName(MidTableConst.ACTOR_2_WORKS)
public class Actor2Works implements Serializable {

    private static final long serialVersionUID = 2732726676816603209L;

    Long actorId;

    Long worksId;

}
