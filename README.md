# rocketmq-second

基于spring boot运行的rocket mq,【传达消息是对象】

## start

+ 启动rocket mq,在docker-compose文件夹下【必须先安装docker】

```bash
docker-compose up -d
```

+ 启动项目：
    1. 配置maven settings，路径：setting/settings.xml
    2. 右键Application，然后点击Run 'Application.main()'

+ 测试：运行测试文件ProducerAllTest的send()方法，日志中出现：“消费 body Hello MQ”，表示成功

## 日志

- 2021-08-31 01:40:33.855 INFO 18008 --- [nio-1235-exec-3] com.sample.producer.ObjectController     : 发送 成功 topic1
- 2021-08-31 01:40:33.869 INFO 18008 --- [MessageThread_1] c.sample.consumer.ObjectMessageListener  : 消费 成功 Person(
  name=序列化对象 0)

## 引用

https://github.com/apache/rocketmq-docker

https://code.aliyun.com/aliware_rocketmq/rocketmq-demo/tree/master?spm=a2c4g.11186623.0.0.6b1cb1a5Nhs26w