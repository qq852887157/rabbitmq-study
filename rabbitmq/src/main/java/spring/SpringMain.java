package spring;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author : fys
 * @Date : 2020/7/20
 */
public class SpringMain {
    public static void main(final String[] args) throws InterruptedException {
        AbstractApplicationContext abstractApplicationContext=new ClassPathXmlApplicationContext("classpath:context.xml");
        //RabbitMq模板
        RabbitTemplate rabbitTemplate=abstractApplicationContext.getBean(RabbitTemplate.class);
        //发送消息
        rabbitTemplate.convertAndSend("hello world");
        Thread.sleep(1000);//休眠1秒
        abstractApplicationContext.close();//关闭容器
    }
}