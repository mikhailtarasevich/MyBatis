package com.mt.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegionDto {

    @ApiModelProperty(value = "ID региона", example = "5")
    private Integer id;

    @ApiModelProperty(value = "Название региона", example = "Los-Angeles")
    private String name;


    @ApiModelProperty(value = "Сокращение региона", example = "LA")
    private String abbr;

}
