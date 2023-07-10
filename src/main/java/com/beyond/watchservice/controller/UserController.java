package com.beyond.watchservice.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beyond.watchservice.model.WatchDetail;
import com.beyond.watchservice.service.WatchService;
import com.beyond.watchservice.utils.ExcelUtils;
import com.beyond.watchservice.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "名单")
@Slf4j
@RequestMapping("/admin/user")
@RestController
public class UserController {
    @Autowired
    private WatchService watchService;

    @GetMapping("/getall")
    public Result getAll(@RequestParam(required = false) String wx,
                         @RequestParam(required = false) String content,
                         @RequestParam(required = false) String status,
                         @RequestParam(required = false) String createTime,
                         @RequestParam(required = false) String endTime,
                         @RequestParam(required = false) Integer page,
                         @RequestParam(required = false) Integer pageSize){
        Page<WatchDetail> watchDetails = watchService.getall(wx,content,status,createTime,endTime,page,pageSize);
        return Result.ok(watchDetails);
    }

    @PostMapping("/importFile")
    public Result importFile(@RequestParam MultipartFile file) {
        try {
            ExcelUtils.checkFile(file);
        }catch (Exception e){
            return Result.fail("异常"+e.getMessage());
        }
        watchService.importFile(file);
        return Result.ok();
    }

    @PutMapping("/updateStatus")
    public Result updateStatus(@RequestParam @NotNull Integer id, @RequestParam @NotNull Integer status){
        watchService.updateStatus(id,status);
        return Result.ok();
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(@RequestParam @NotNull Integer id){
        watchService.removeById(id);
        return Result.ok();
    }
    @GetMapping("/watch")
    public Result watch(@RequestParam @NotNull String[] names,@RequestParam @NotNull String url,@RequestParam @NotNull String serverName){
        watchService.getWatch(names,url,serverName);
        return Result.ok();
    }
    @GetMapping("/send")
    public Result send(@RequestParam @NotNull String[] names,@RequestParam @NotNull String url,@RequestParam @NotNull String serverName){
        watchService.send(names,url,serverName);
        return Result.ok();
    }
}

