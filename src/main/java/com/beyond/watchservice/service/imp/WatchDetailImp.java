package com.beyond.watchservice.service.imp;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beyond.watchservice.dao.WatchDetailMapper;
import com.beyond.watchservice.model.WatchDetail;
import com.beyond.watchservice.model.vo.UserVo;
import com.beyond.watchservice.service.WatchService;
import com.beyond.watchservice.utils.ExcelUtils;
import com.beyond.watchservice.utils.MQCode;
import com.beyond.watchservice.utils.RabbitMqUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class WatchDetailImp extends ServiceImpl<WatchDetailMapper, WatchDetail> implements WatchService {
    @Autowired
    private RabbitMqUtils rabbitMqUtils;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Page<WatchDetail> getall(String wx, String content, String status, String createTime, String endTime,Integer page,Integer pageSize) {
        LambdaQueryWrapper<WatchDetail> queryWrapper = new LambdaQueryWrapper<>();
        if (!ObjectUtil.isEmpty(wx)){
            queryWrapper.like(WatchDetail::getName,wx);
        }
        if (!ObjectUtil.isEmpty(content)){
            queryWrapper.like(WatchDetail::getContent,content);
        }
        if (!ObjectUtil.isEmpty(status)){
            queryWrapper.eq(WatchDetail::getStatus,status);
        }
        if (!ObjectUtil.isEmpty(createTime)&&!ObjectUtil.isEmpty(endTime)){
            queryWrapper.between(WatchDetail::getCreated,createTime,endTime);
        }
        queryWrapper.orderByDesc(WatchDetail::getCreated);
        Page<WatchDetail> pageinfo = this.page(new Page<>(page, pageSize), queryWrapper);

        return pageinfo;
    }

    @Override
    public void importFile(MultipartFile file) {
        //获取文件中的信息
        List<UserVo> userVos = ExcelUtils.extractDataFormExcel(file);
        //将获得的文件保存
        userVos.forEach(item->{
            WatchDetail watchDetail = new WatchDetail();
            watchDetail.setName(item.getVx());
            watchDetail.setContent(item.getContent());
            this.save(watchDetail);
        });
    }

    @Override
    public void updateStatus(Integer id, Integer status) {
        //获得对应的信息
        WatchDetail watchDetail = this.getById(id);
        watchDetail.setStatus(status);
        watchDetail.setSendTime(LocalDateTime.now());
        this.updateById(watchDetail);
    }

    @Override
    public void getWatch(String[] names, String url, String serverName) {
        String content="服务："+serverName+" 服务器地址："+url+" 出现异常请及时查看";

        for (String name : names) {
            WatchDetail watchDetail = new WatchDetail();
            watchDetail.setContent(content);
            watchDetail.setServerName(serverName);
            watchDetail.setName(name);
            watchDetail.setUrl(url);
            this.save(watchDetail);
        }

    }

    @Override
    public void send(String[] names, String url, String serverName) {
        //rabbitMqUtils.sendMessage(name, MQCode.QUEUE_NAME)；
        for (String name : names) {
            WatchDetail watchDetail = new WatchDetail();
            watchDetail.setName(name);
            watchDetail.setUrl(url);
            watchDetail.setServerName(serverName);
            String s = JSONUtil.toJsonStr(watchDetail);
            rabbitTemplate.convertAndSend("watch_topic_exchange","watch.normal",s);
        }

    }
}

