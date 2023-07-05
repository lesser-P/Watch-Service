package com.beyond.watchservice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyond.watchservice.dao.UserInfoMapper;
import com.beyond.watchservice.model.UrlInfo;
import com.beyond.watchservice.model.UserInfo;
import com.beyond.watchservice.model.dto.UserDTO;
import com.beyond.watchservice.service.UrlService;
import com.beyond.watchservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService {
    @Autowired
    private UrlService urlService;
    @Override
    public void saveDetail(UserDTO userDTO) {
        String userId = userDTO.getUserId();
        String url = userDTO.getUrl();
        String serverName = userDTO.getServerName();
        //查看userinfo中是否有对应的userid
        UserInfo user = this.getOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserId, userId));
        if (ObjectUtil.isEmpty(user)){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            this.save(userInfo);
            //保存地址数据
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setUserId(userInfo.getId());
            urlInfo.setUrl(url);
            urlInfo.setServerName(serverName);
            urlService.save(urlInfo);
        }else  {
            Long id = user.getId();
            UrlInfo urlInfo = new UrlInfo();
            urlInfo.setUserId(id);
            urlInfo.setServerName(serverName);
            urlInfo.setUrl(url);
            urlService.save(urlInfo);
        }
    }
}

