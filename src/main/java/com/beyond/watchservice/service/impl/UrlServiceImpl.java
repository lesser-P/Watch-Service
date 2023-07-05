package com.beyond.watchservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyond.watchservice.dao.UrlInfoMapper;
import com.beyond.watchservice.model.UrlInfo;
import com.beyond.watchservice.service.UrlService;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl extends ServiceImpl<UrlInfoMapper, UrlInfo> implements UrlService {
}

