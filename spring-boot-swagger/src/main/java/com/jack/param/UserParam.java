package com.jack.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "com.jack.param.UserParam", description = "用户参数")
public class UserParam {

    @ApiModelProperty(value = "用户ID")
    private String uid;

    @ApiModelProperty(value = "用户名称")
    private String uname;

    @ApiModelProperty(value = "用户年龄")
    private int uage;

}
