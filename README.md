# rabbit-tools

A collection of simple rabbit tools written in Java for use in integration tests and other simple applications

# Installation

rabbit-tools can be included in your Maven project by adding the following dependency

```
<dependency>
    <groupId>uk.gov.ons.tools</groupId>
    <artifactId>rabbit</artifactId>
    <version>1.0.0</version>
</dependency>
```

# Configuration 

There is a configuration helper class, uk.gov.ons.tools.rabbit.Rabbitmq, included in this library that can be used to deserialise rabbit details from Spring configuration in application.yml or similar

```
rabbitmq:
  username: guest
  password: guest
  host: localhost
  port: 6672
  virtualhost: /
  cron: "* * * * * ?"
```

```java
@Configuration
@Data
public class AppConfig {
    private Rabbitmq rabbitmq;
}
```

```java
public class SomeClass {
    
    @Autowired
    private AppConfig appConfig;
    
    private void someMethod(){
        Rabbitmq rabbitmq = this.appConfig.getRabbitmq();
        String rabbitHost = rabbitmq.getHost();
    }
}
```

# Sending messages

```java
Rabbitmq config = this.appConfig.getRabbitmq();

SimpleMessageSender sender = new SimpleMessageSender(config);

sender.sendMessage("sample-outbound-exchange", "Sample.Message.binding", "message content (json etc)");
```

# Receiving messages

```java
Rabbitmq config = this.appConfig.getRabbitmq();
SimpleMessageListener sml = new SimpleMessageListener(config);

BlockingQueue<String> msgQueue = sml.listen(SimpleMessageListener.ExchangeType.Direct,
                "sample-outbound-exchange", "Sample.SampleMessage.binding");

String message = msgQueue.take();
sml.close();
```

