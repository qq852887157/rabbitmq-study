package springboot.customer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author : fys
 * @Date : 2020/8/1
 */
@Component
//注解指定目标方法来作为消费消息的方法
@RabbitListener(queuesToDeclare = @Queue("hello"))
public class helloCustomer {
    @RabbitHandler
    public void receivel(String message){
        System.out.println(message);
    }
}
