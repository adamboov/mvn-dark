package com.adam.dark.vo;

import com.adam.dark.base.BaseVO;
import com.adam.dark.base.enmus.SexTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class ActorVO extends BaseVO {

    private static final long serialVersionUID = -3220721952111624283L;


    String name;

    SexTypeEnum gender;

    LocalDate birthday;

    LocalDateTime birthdayDateTime;

    LocalTime birthdayTime;

    @JsonIgnoreProperties("actorList")
    List<WorksVO> worksList;

}
