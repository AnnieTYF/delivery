package com.tyf.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class CircleDto {

    @ApiModelProperty(value = "社交圈名称")
    private String name;

    @ApiModelProperty(value = "管理者学号")
    private String manager;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
