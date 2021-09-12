package confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @Description:confirm模式 异步模式
 * @Author : fys
 * @Date : 2020/7/17
 */
public class Send2 {
    private static final String QUEUE_NAME="test_queue_confirm2";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取rabbitmq连接
        Connection connection=ConnectionUtil.connection();
        //创建通道
        Channel channel=connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //生产者调用confirmSelect 将channel 设置为confirm模式
        channel.confirmSelect();
        //未确认消息
        final SortedSet<Long> confirmSet= Collections.synchronizedSortedSet(new TreeSet<Long>());
        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            //成功调handleAck
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleAck--multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("handleAck--multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //失败调handleNack
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if(multiple){
                    System.out.println("handleNack--multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("handleNack--multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        String s="asdas";
        while (true){
           long seqNo=channel.getNextPublishSeqNo();
           channel.basicPublish("",QUEUE_NAME,null,s.getBytes());
           confirmSet.add(seqNo);
        }

    }
}
