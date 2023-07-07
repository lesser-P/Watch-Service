package com.beyond.watchservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.beyond.watchservice.model.InfoVo;
import com.beyond.watchservice.model.UserInfo;
import com.beyond.watchservice.model.dto.UserDTO;

import java.util.List;

public interface UserService extends IService<UserInfo> {
    void saveDetail(UserDTO userDTO);

    List<InfoVo> getAll();
}
