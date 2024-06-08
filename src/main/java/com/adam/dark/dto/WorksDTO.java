package com.adam.dark.dto;

import com.adam.dark.base.BaseVO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;

import java.util.List;

@ApiModel
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorksDTO extends BaseVO {

    private static final long serialVersionUID = 4001621953759758653L;

    @ApiModelProperty("作品名称")
    String name;

    @ApiModelProperty("作品描述")
    String description;

    @ApiModelProperty("作品地址")
    String url;

    @ApiModelProperty("演员集合")
    @JsonIgnoreProperties("worksList")
//    @ToString.Exclude
    List<ActorDTO> actorList;
}
