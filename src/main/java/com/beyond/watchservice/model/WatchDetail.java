package com.beyond.watchservice.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("watch_list")
public class WatchDetail {


    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("content")
    private String content;

    @TableField("status")
    private Integer status;

    @TableField("created")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @TableField("sendtime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sendTime;

    @TableField("message")
    private String message;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField("url")
    private String url;

    @TableField("servername")
    private String serverName;

}

