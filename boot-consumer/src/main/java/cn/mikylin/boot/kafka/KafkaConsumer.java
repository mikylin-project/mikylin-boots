package cn.mikylin.boot.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka mq 消费者包装类
 *
 * @author mikylin
 * @date 20200122
 */
@Component
public class KafkaConsumer {

    /**
     * 监听方法，可以一次性监听多个 topic
     * @param cr  kafka 返回的消息包装类
     */
    @KafkaListener(topics = {"test-topic" /*,"test-topic-2"*/ })
    public void consume(ConsumerRecord<String,String> cr) {
        // value
        String value = cr.value();
        System.out.println(value);
        // key
        String key = cr.key();
        System.out.println(key);
        // 读取指针
        long offset = cr.offset();
        System.out.println(offset);
        // 读取的分区编号
        int partition = cr.partition();
        System.out.println(partition);
        // topic
        String topic = cr.topic();
        System.out.println(topic);
    }
}
