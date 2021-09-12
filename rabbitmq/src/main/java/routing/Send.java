package routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:路由器发送消息
 * @Author : fys
 * @Date : 2020/7/9
 */
public class Send {
    private static final String EXCHANGE_NAME="test_exchange_direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg="hellp direct";
        channel.basicPublish(EXCHANGE_NAME,"error",null,msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"info",null,msg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"warning",null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
