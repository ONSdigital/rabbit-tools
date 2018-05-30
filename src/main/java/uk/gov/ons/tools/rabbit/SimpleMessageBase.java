package uk.gov.ons.tools.rabbit;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class SimpleMessageBase {
    /**
     * The type of the exchange to listen for messages on
     */
    public enum ExchangeType { Direct, Topic, Fanout }

    private ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;

    public SimpleMessageBase(String host, int port, String username, String password){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        this.connectionFactory = connectionFactory;

        rabbitAdmin = new RabbitAdmin(connectionFactory);
    }

    public SimpleMessageBase(Rabbitmq rabbitmq){
        this(rabbitmq.getHost(), rabbitmq.getPort(), rabbitmq.getUsername(), rabbitmq.getPassword());
    }

    protected RabbitAdmin getRabbitAdmin(){
        return this.rabbitAdmin;
    }

    protected ConnectionFactory getConnectionFactory(){
        return this.connectionFactory;
    }

    protected RabbitTemplate getRabbitTemplate(){
        return this.rabbitAdmin.getRabbitTemplate();
    }
}
