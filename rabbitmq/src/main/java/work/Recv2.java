package work;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:接收信息
 * @Author : fys
 * @Date : 2020/7/13
 */
public class Recv2 {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        final Channel channel=connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicQos(1);
        DefaultConsumer consumer=new DefaultConsumer(channel){
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String msg=new String(body,"UTF-8");
              System.out.println("消费者2"+msg);
              try {
                  Thread.sleep(1000);
              }catch (Exception e){
                  e.printStackTrace();
              }finally {
                  System.out.println("消费者2 读完了");
                  //手动应答
                  channel.basicAck(envelope.getDeliveryTag(),false);
              }
          }
        };
        //自动应答 true 手动应答false
        //boolean autoAck=true;
        boolean autoAck=false;
        //监听队列
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);


    }
}
