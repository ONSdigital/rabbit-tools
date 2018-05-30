package uk.gov.ons.tools.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class SimpleMessageSender extends SimpleMessageBase {

    public SimpleMessageSender(String host, int port, String username, String password) {
        super(host, port, username, password);
    }

    /**
     * Constructor that accepts a Rabbitmq configuration object
     * @param rabbitmq a Rabbitmq configuration object populated by Spring or other means
     */
    public SimpleMessageSender(Rabbitmq rabbitmq) {
        super(rabbitmq);
    }

    public void sendMessage(String exchange, String routingKey, String message){
        RabbitTemplate rabbitTemplate = getRabbitTemplate();

        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public void sendMessage(String exchange, String message){
        RabbitTemplate rabbitTemplate = getRabbitTemplate();

        rabbitTemplate.convertAndSend(exchange, message);
    }
}
