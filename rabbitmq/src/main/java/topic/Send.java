package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:topic发送消息
 * @Author : fys
 * @Date : 2020/7/9
 */
public class Send {
    private static final String EXCHANGE_NAME="test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String addmsg="商品添加";
        String delmsg="商品删除";
        String updmsg="商品更新";
        channel.basicPublish(EXCHANGE_NAME,"goods.add",null,addmsg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,delmsg.getBytes());
        channel.basicPublish(EXCHANGE_NAME,"goods.update",null,updmsg.getBytes());
        System.out.println("send");
        channel.close();
        connection.close();
    }
}
