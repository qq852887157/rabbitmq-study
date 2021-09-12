package routing;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:交换机接收信息
 * @Author : fys
 * @Date : 2020/7/13
 */
public class Recv2 {
    private static final String QUEUE_NAME="test_queue_direct_2";
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        final Channel channel=connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定队列到交换机 转发器
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"warning");
        channel.basicQos(1);//保证一次只有一个
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"UTF-8");
                System.out.println("消费者2"+msg);
                try {
                    Thread.sleep(2000);
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
