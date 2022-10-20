package com.thinkdifferent.mq.consumer;

import com.thinkdifferent.convertopic.service.ConvertPicService;
import com.thinkdifferent.convertopic.task.PicTask;
import com.thinkdifferent.mq.config.RabbitMQConfig;
import net.sf.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertPicConsumer {

    @Autowired
    private PicTask task;
    @Autowired
    private ConvertPicService convertPicService;

    /**
     * 队列消费者-转换MP4文件。启动多线程任务，处理队列中的消息
     *
     * @param strData 队列中放入的JSON字符串
     */
    @RabbitListener(queues  = RabbitMQConfig.QUEUE_RECEIVE)
    public void receiveTodoRequestByMap(String strData){
        try{
            if(RabbitMQConfig.consumer){
                JSONObject jsonData = JSONObject.fromObject(strData);
                task.doTask(convertPicService, jsonData);
//                	      Thread.currentThread().join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
