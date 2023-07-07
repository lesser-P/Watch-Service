package com.beyond.watchservice.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.beyond.watchservice.model.InfoVo;
import com.beyond.watchservice.model.UrlInfo;
import com.beyond.watchservice.model.UserInfo;
import com.beyond.watchservice.model.dto.UserDTO;
import com.beyond.watchservice.service.UrlService;
import com.beyond.watchservice.service.UserService;
import com.beyond.watchservice.utils.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "名单")
@Slf4j
@RequestMapping("/admin/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UrlService urlService;
    //添加路径
    @PostMapping("/add")
    public Result addList(@RequestBody UserDTO userDTO){
        userService.saveDetail(userDTO);
        return Result.ok();
    }
    @GetMapping("/list")
    public Result list (){
        List<InfoVo> infoVoList= userService.getAll();
        return Result.ok(infoVoList);
    }
}

