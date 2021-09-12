package simple;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:接收信息
 * @Author : fys
 * @Date : 2020/7/13
 */
public class Recv {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        /**
         * @Author fys
         * @Description //过时api
         * @Date 2020/7/13
         * @Param
         * @return
        */
//        //定义队列消费者
//        QueueingConsumer consumer=new QueueingConsumer(channel);
//        //监听队列
//        channel.basicConsume(QUEUE_NAME,true,consumer);
//        while (true){
//            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
//            String msg=new String(delivery.getBody());
//            System.out.println("接收的信息"+msg);
//        }

        /**
         * @Author fys
         * @Description //新api
         * @Date 2020/7/13
         * @Param
         * @return
        */
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        DefaultConsumer consumer=new DefaultConsumer(channel){
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String msg=new String(body,"UTF-8");
              System.out.println("接收"+msg);
          }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);


    }
}
