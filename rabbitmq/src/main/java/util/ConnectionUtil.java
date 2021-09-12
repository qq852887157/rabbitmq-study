package util;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @Author : fys
 * @Date : 2020/7/9
 */
public class ConnectionUtil {
    /**
     * @Author fys
     * @Description //获取rabbitmq 连接
     * @Date 2020/7/9
     * @Param
     * @return
    */
    public static Connection connection() throws IOException, TimeoutException {
        //定义一个连接工厂
        ConnectionFactory factory=new ConnectionFactory();
        //设置服务地址
        factory.setHost("10.211.55.22");
        //设置amqp端口
        factory.setPort(5672);
        //设置数据库 vhost
        factory.setVirtualHost("/vhost_mmr");
        //设置用户名
        factory.setUsername("fys");
        //设置密码
        factory.setPassword("123456");

        return factory.newConnection();
    }
}
