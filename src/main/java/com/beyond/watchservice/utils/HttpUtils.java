//package com.beyond.watchservice.utils;
//
//import cn.hutool.http.HttpRequest;
//import cn.hutool.http.HttpResponse;
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class HttpUtils {
//    public static void main(String[] args) {
//        String error;
//        try{
//            HttpResponse response = HttpRequest.get("http://localhost:8080/admin/system/task/getall").execute();
//            String body = response.body();
//            RespBody respBody = JSON.parseObject(body, RespBody.class);
//            boolean ok = response.isOk();
//            if (respBody.getCode().equals(404)){
//                error="服务器挂了";
//            }
//            System.out.println(body+ok);
//        }catch (Exception e){
//            log.info("e,{}",e);
//            error="服务器挂了";
//        }
//
//    }
//}
//
