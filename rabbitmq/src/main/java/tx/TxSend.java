package tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:事务机制
 * @Author : fys
 * @Date : 2020/7/17
 */
public class TxSend {
    private static final String QUEUE_NAME="test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg="tx";
        try {
            //开启事务模式
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

            //制造异常
            int i=1/0;
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();
            System.out.println("发送失败回滚");
        }
        channel.close();
        connection.close();

    }
}
