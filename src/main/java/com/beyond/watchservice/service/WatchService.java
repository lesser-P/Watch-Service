package com.beyond.watchservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.beyond.watchservice.model.WatchDetail;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

public interface WatchService extends IService<WatchDetail> {
    Page<WatchDetail> getall(String wx, String content, String status, String createTime, String endTime, Integer page, Integer pageSize);

    void importFile(MultipartFile file);

    void updateStatus(Integer id, Integer status);

    void getWatch(String[] name, String url, String serverName);

    void send(String name[], String url, String serverName);
}
