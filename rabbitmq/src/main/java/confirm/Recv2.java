package confirm;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:事务机制消费者
 * @Author : fys
 * @Date : 2020/7/13
 */
public class Recv2 {
    private static final String QUEUE_NAME="test_queue_confirm2";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        final Channel channel=connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg=new String(body,"UTF-8");
                System.out.println("消费者1"+msg);
            }
        };
        boolean autoAck=true;
        //监听队列
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);



    }
}
