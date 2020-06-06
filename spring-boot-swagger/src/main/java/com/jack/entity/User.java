package com.jack.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "com.jack.entity.User", description = "用户信息")
public class User {

    @ApiModelProperty(value = "用户ID")
    private String uid;

    @ApiModelProperty(value = "用户名称")
    private String uname;

}
