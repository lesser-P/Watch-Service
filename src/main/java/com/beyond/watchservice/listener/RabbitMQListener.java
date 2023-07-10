package com.beyond.watchservice.listener;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.beyond.watchservice.model.WatchDetail;
import com.beyond.watchservice.service.WatchService;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class RabbitMQListener {
    @Autowired
    private WatchService watchService;

    @RabbitListener(queues = "watch_queue")
    public void ListenerQueue(Message message, Channel channel) throws IOException {
        //如果消息成功处理则调用channel的basicAck签收
        //如果消息处理失败则调用channel的basicNack拒绝签收，broker重新发送给consumer
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            //接受转换消息
            System.out.println(new String(message.getBody()));

            WatchDetail watchDetail = JSONUtil.toBean(new String(message.getBody()), WatchDetail.class);
            String serverName = watchDetail.getServerName();
            String url = watchDetail.getUrl();
            String content="服务："+serverName+" 服务器地址："+url+" 出现异常请及时查看";
            watchDetail.setContent(content);
            watchService.save(watchDetail);
            channel.basicAck(deliveryTag,false);
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.toString());
            //拒绝签收
            //requeue:是否重回队列，如果设为true则消息重回queue，broker会重新发送消息给消费端
            channel.basicReject(deliveryTag,false);
        }
    }

}

