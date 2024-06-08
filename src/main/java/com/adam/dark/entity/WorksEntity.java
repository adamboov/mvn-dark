package com.adam.dark.entity;


import com.adam.dark.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@TableName("bs_works")
@Table(appliesTo = "bs_works", comment = "作品表")
@Entity(name = "bs_works")
public class WorksEntity extends BaseEntity {

    private static final long serialVersionUID = -2981098143824763607L;

    @Comment("作品名称")
    String name;

    @Comment("作品描述")
    String description;

    @Comment("作品地址")
    String url;

    @ToString.Exclude
    @Comment("演员列表")
    @ManyToMany(mappedBy = "worksList")
    List<ActorEntity> actorList = new ArrayList<>();

}
