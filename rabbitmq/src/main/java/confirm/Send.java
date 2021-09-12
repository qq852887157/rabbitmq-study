package confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:confirm模式串行模式
 * @Author : fys
 * @Date : 2020/7/17
 */
public class Send {
    private static final String QUEUE_NAME="test_queue_confirm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect 将channel 设置为confirm模式
        channel.confirmSelect();
        // 批量发送
        for (int i = 0; i < 10; i++) {
            String msg="confirm"+i;
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        }
        if(!channel.waitForConfirms()){
            System.out.println("发送失败");
        }else {
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();

    }
}
