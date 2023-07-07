package com.beyond.watchservice.model;

import lombok.Data;

@Data
public class InfoVo extends Base{
    private String userId;
    private String url;
    private String serverName;
}

