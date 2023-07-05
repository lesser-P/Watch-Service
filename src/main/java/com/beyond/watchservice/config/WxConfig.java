package com.beyond.watchservice.config;

import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxConfig {
    @Value("${wx.cp.corpId}")
    private String corpId;
    @Value("${wx.cp.agentId}")
    private Integer agentId;
    @Value("${wx.cp.secret}")
    private String secret;
    public WxCpService getWxCpService() {
        WxCpService wxCpService=new WxCpServiceImpl();
        WxCpDefaultConfigImpl config =new WxCpDefaultConfigImpl();
        config.setAgentId(agentId);
        config.setCorpSecret(secret);
        config.setCorpId(corpId);
        wxCpService.setWxCpConfigStorage(config);
        return wxCpService;
    }

}

