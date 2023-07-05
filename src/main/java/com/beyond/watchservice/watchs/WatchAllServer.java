package com.beyond.watchservice.watchs;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beyond.watchservice.config.WxConfig;
import com.beyond.watchservice.model.RespBody;
import com.beyond.watchservice.model.UrlInfo;
import com.beyond.watchservice.service.UrlService;
import com.beyond.watchservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpMessageService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.message.WxCpMessage;
import me.chanjar.weixin.cp.config.WxCpConfigStorage;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WatchAllServer {
    @Resource
    private WxConfig wxConfig;
    @Resource
    private UserService userService;
    @Resource
    private UrlService urlService;
    //遍历获取所有url,将url依次执行

    @Scheduled(cron = "0/5 * * * * ?")
    public void watchMission(){
        List<String> urls = urlService.list().stream().map(UrlInfo::getUrl).collect(Collectors.toList());
        urls.forEach(url->{
            watchLogic(url);
        });
    }
    public void watchLogic(String url){
        String userId;
        try{
            //向服务发起请求
            //得到请求结果
            HttpResponse response = HttpRequest.get(url).execute();
            RespBody respBody = JSON.parseObject(response.body(), RespBody.class);
            if (respBody.getCode().equals(404)){
                //404则调用sendMessage(),反之忽略
                UrlInfo urlInfo = urlService.getOne(new LambdaQueryWrapper<UrlInfo>()
                        .eq(UrlInfo::getUrl, url));
                 userId= userService.getById(urlInfo.getUserId()).getUserId();
                sendMessage(url,userId,urlInfo.getServerName());
            }
        }catch (Exception e){
            UrlInfo urlInfo = urlService.getOne(new LambdaQueryWrapper<UrlInfo>()
                    .eq(UrlInfo::getUrl, url));
            userId= userService.getById(urlInfo.getUserId()).getUserId();
            sendMessage(url+"异常日志"+e.getMessage(),userId,urlInfo.getServerName());
        }
    }

    public void sendMessage(String url,String userId,String name){
        WxCpService wxCpService = wxConfig.getWxCpService();
        String content="服务"+name+"地址"+url+"响应异常";
        WxCpMessage message = WxCpMessage.TEXT().content(content).toUser(userId).build();
        WxCpMessageService messageService = wxCpService.getMessageService();
        try{
            messageService.send(message);
        }catch (WxErrorException wxe){
            log.info("error,{}",wxe);
        }
    }


}

