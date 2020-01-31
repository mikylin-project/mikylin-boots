package cn.mikylin.boot.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * kafka mq 生产者包装类
 *
 * @author mikylin
 * @date 20200122
 */
@Component
public class KafkaProductor {

    @Resource
    KafkaTemplate<String,String> kt;

    /**
     * 发送消息的方法
     * @param topic  创建的 topic
     * @param partition  topic 分片编号，从 0 开始
     * @param key  消息 key，主要用来分片和作为压缩凭据，可以重复，可以为空
     * @param message  消息主体
     */
    public void send(String topic,Integer partition,String key,String message) {
        /**
         * 关于 key，需要注意的是，
         * 如果 kafka 启动了日志压缩 (compact) 模式，则不允许为 null
         */
        kt.send(topic,partition,key,message);
    }

    public void send(String message) {
        send("test-topic",null,null,message);
    }


}
