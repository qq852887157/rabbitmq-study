package work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author : fys
 * @Date : 2020/7/13
 */
public class Send {
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection= ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //每个消费者只能发一个
        channel.basicQos(1);
        for(int i=0;i<50;i++){
            String msg="hello "+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
        }
        channel.close();
        connection.close();
    }
}
