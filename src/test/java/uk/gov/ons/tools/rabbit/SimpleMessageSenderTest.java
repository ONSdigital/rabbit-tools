package uk.gov.ons.tools.rabbit;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@RunWith(MockitoJUnitRunner.class)
public class SimpleMessageSenderTest {

  @InjectMocks
  private SimpleMessageSender sender;

  @Mock
  private RabbitAdmin rabbitAdmin;

  @Mock
  private RabbitTemplate rabbitTemplate;

  @Before
  public void setUp(){
    when(this.rabbitAdmin.getRabbitTemplate()).thenReturn(this.rabbitTemplate);
  }

  @Test
  public void testSendMessageToQueue(){
    String queueName  = "Queue.Name";
    String message = "\n"
        + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<dataset>\n"
        + "\t<register>\n"
        + "\t\t<cpf>28409746816</cpf>\n"
        + "\t\t<cnpj>17443311000128</cnpj>\n"
        + "\t\t<ssn>591838486</ssn>\n"
        + "\t\t<number>91</number>\n"
        + "\t\t<name>Graig Woodcock</name>\n"
        + "\t\t<password>K,y899gY&+gV7Mp</password>\n"
        + "\t\t<cc>38732647699838</cc>\n"
        + "\t\t<lorem>In fermentum et sollicitudin ac orci phasellus egestas tellus rutrum. Et sollicitudin ac orci. Nibh mauris cursus mattis. Enim diam vulputate ut pharetra sit amet aliquam. Bibendum ut tristique et egestas. In massa tempor nec feugiat nisl pretium fusce id velit.</lorem>\n"
        + "\t</register></dataset>";

    this.sender.sendMessageToQueue(queueName, message);

    ArgumentCaptor<String> queueNameCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    verify(this.rabbitTemplate).convertAndSend(queueNameCaptor.capture(), messageCaptor.capture());

    assertEquals(queueName, queueNameCaptor.getValue());
    assertEquals(message, messageCaptor.getValue());
  }
}
