package com.beyond.watchservice.model;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_info")
public class UserInfo extends Base{
    @TableField("userid")
    private String userId;
}

