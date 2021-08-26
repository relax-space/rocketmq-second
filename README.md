# rocketmq-first

基于spring boot运行的rocket mq

## start

+ 启动rocket mq,在docker-compose文件夹下【必须先安装docker】

```bash
docker-compose up -d
```

+ 启动项目：
  1. 配置maven settings，路径：setting/settings.xml
  2. 右键Application，然后点击Run 'Application.main()'

+ 测试：运行测试文件ProducerAllTest的send()方法，日志中出现：“消费 body Hello MQ”，表示成功

## 引用

https://github.com/apache/rocketmq-docker

https://code.aliyun.com/aliware_rocketmq/rocketmq-demo/tree/master?spm=a2c4g.11186623.0.0.6b1cb1a5Nhs26w