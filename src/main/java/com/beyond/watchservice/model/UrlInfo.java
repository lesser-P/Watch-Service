package com.beyond.watchservice.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class UrlInfo extends Base{
    @TableField("url")
    private String url;

    @TableField("server_name")
    private String serverName;

    @TableField("userid")
    private Long userId;

}

