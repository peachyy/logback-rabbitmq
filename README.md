# logback-rabbitmq
logback日志搜集到rabbitmq插件

Useage:

队列默认的名称为:`logback_rabbitmq_queue`
```
    <appender name="rabbmitmq" class="com.peachyy.logback.RabbitMQAppender">
        <encoder class="com.peachyy.logback.encoder.RabbitMQencoder">
            <pattern>%-15d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{50}[%file:%line] - %msg%n %ex{2}
            </pattern>
        </encoder>
        <host>localhost</host>
        <port>5672</port>
        <username>usename</username>
        <password>password</password>
        <virtual>/</virtual>
        <spring>false</spring>
        <!--<queue></queue>-->
    </appender>


    <root level="INFO">
       <appender-ref ref="rabbmitmq"/>
    </root>
```


