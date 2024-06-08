package com.adam.dark.entity;

import com.adam.dark.base.BaseEntity;
import com.adam.dark.base.consts.MidTableConst;
import com.adam.dark.base.consts.NumConst;
import com.adam.dark.base.enmus.SexTypeEnum;
import com.adam.dark.entity.mid.Actor2Works;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import com.github.yulichang.annotation.EntityMapping;
import com.github.yulichang.annotation.FieldMapping;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author adamboov
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldNameConstants
@FieldDefaults(level = AccessLevel.PRIVATE)
@TableName("bs_actor")
@Table(appliesTo = "bs_actor", comment = "演员表")
@Entity(name = "bs_actor")
public class ActorEntity extends BaseEntity {

    private static final long serialVersionUID = 6957694709625765873L;

    @Comment("名字")
    @Column(nullable = false, length = NumConst.INT_64)
    String name;

    @Enumerated(EnumType.ORDINAL)
    @Comment("性别")
    @Column(nullable = false)
    SexTypeEnum gender;

    @Comment("出生时间    年月日（即生日）")
    LocalDate birthday;

    @Comment("出生时间  年月日 时分秒")
    LocalDateTime birthdayDateTime;

    @Comment("出生时间 时分秒")
    LocalTime birthdayTime;

    @ToString.Exclude
    @TableField(exist = false)

    @Comment("作品列表")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = MidTableConst.ACTOR_2_WORKS
            , joinColumns = {@JoinColumn(name = Actor2Works.Fields.actorId, referencedColumnName = BaseEntity.Fields.id)}
            , inverseJoinColumns = {@JoinColumn(name = Actor2Works.Fields.worksId, referencedColumnName = BaseEntity.Fields.id)}
    )
    List<WorksEntity> worksList = new ArrayList<>();

    @TableField(exist = false)
    @FieldMapping(tag = Actor2Works.class, thisField = BaseEntity.Fields.id,
            joinField = Actor2Works.Fields.actorId, select = "works_id")

    @Transient
    List<Long> worksIdList;

}
