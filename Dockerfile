# 该镜像需要依赖的基础镜像
FROM java:8

#执行任务时间和日志时间调整
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

# 声明服务运行在xxx端口
#启动容器执行命令  -Djava.security.egd=file:/dev/./urandom 可以缩短tomcat启动时间
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/data/watch-service.jar"]
