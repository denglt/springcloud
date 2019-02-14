package dlt.study.springcloud.userserver;

import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class ConsumerDemo {

    private Logger Log = LoggerFactory.getLogger(this.getClass());
    private static Properties props = new Properties();
    private static String topic = "userTopic";

    static {
        String zooKeeper = "127.0.0.1:2181";
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // props.put("zookeeper.connect",zooKeeper);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "GroupA");//必须要
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);// 自动提交偏移量（offset）//禁用自动提交，并只有在线程完成处理后才为记录手动提交偏移量
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG //  最大poll的间隔，可以为消费者提供更多的时间去处理返回的消息，缺点是此值越大将会延迟组重新平衡
        //ConsumerConfig.MAX_POLL_RECORDS_CONFIG // 此设置限制每次调用poll返回的消息数
        //ConsumerConfig.AUTO_OFFSET_RESET_CONFIG // 决定consumer如何reset offset
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    @Test
    public void subscribe() {
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "G_COUNT_2");
       // props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                Log.info("onPartitionsRevoked -> " + Joiner.on(",").join(partitions));
                position();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                Log.info("onPartitionsAssigned -> " + Joiner.on(",").join(partitions));
                position();
            }

            private void position() {
                //System.out.println(consumer.assignment().size());
                consumer.assignment().forEach((tp) -> {
                    long position = consumer.position(tp);
                    Log.info(String.format("group=%s, topic = %s, partition = %d, offset = %d", props.getProperty(ConsumerConfig.GROUP_ID_CONFIG),
                            tp.topic(), tp.partition(), position));
                });
            }
        });

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            // consumer.pause();consumer.resume();
            for (ConsumerRecord<String, String> record : records) {
                long timestamp = record.timestamp();
                String s = record.timestampType().toString();
                String timeStr = s + ":" + timestamp;
                Log.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s , time = %s",
                        record.topic(), record.partition(), record.offset(), record.key(), record.value(), timeStr));
            }
        }
    }





    /**
     * 与消费者无关
     * Get the first offset for the given partitions.
     */
    @Test
    public void beginningOffsets() {
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        consumer.beginningOffsets(Lists.newArrayList(Iterables.transform(partitionInfos, t -> new TopicPartition(t.topic(), t.partition()))))
                .forEach((tp, p) -> Log.info(String.format("topic = %s, partition = %d, offset = %d", tp.topic(), tp.partition(), p)));
    }

    /**
     * 与消费者无关
     * Get the last offset for the given partitions.
     * The last offset of a partition is the offset of the upcoming message, i.e. the offset of the last available message + 1.
     */
    @Test
    public void endOffsets() {
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "dengltxx");
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        consumer.endOffsets(Lists.newArrayList(Iterables.transform(partitionInfos, t -> new TopicPartition(t.topic(), t.partition()))))
                .forEach((tp, p) -> Log.info(String.format("topic = %s, partition = %d, offset = %d", tp.topic(), tp.partition(), p)));
    }

    @Test
    public void consumerWithOutGroupID() { //org.apache.kafka.common.errors.InvalidGroupIdException: The configured groupId is invalid
        props.remove(ConsumerConfig.GROUP_ID_CONFIG);
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Lists.newArrayList(topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records)
                Log.info(String.format("topic = %s, partition = %d, offset = %d, key = %s, value = %s", record.topic(), record.partition(), record.offset(), record.key(), record.value()));
        }
    }


    @Test
    public void infoTopic() {
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "G_COUNT_2");
        Consumer<String, String> consumer = new KafkaConsumer<>(props);
        List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        Log.info("begin offsets:");
        consumer.beginningOffsets(Lists.transform(partitionInfos, t -> new TopicPartition(t.topic(), t.partition())))
                .forEach((tp, p) -> Log.info(String.format("topic = %s, partition = %d, offset = %d", tp.topic(), tp.partition(), p)));

        Log.info("end offsets:");
        consumer.endOffsets(Lists.transform(partitionInfos, t -> new TopicPartition(t.topic(), t.partition())))
                .forEach((tp, p) -> Log.info(String.format("topic = %s, partition = %d, offset = %d", tp.topic(), tp.partition(), p)));
    }

}
