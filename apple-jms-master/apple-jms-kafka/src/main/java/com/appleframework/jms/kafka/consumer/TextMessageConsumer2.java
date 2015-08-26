package com.appleframework.jms.kafka.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appleframework.jms.core.consumer.MessageConusmer2;
import com.appleframework.jms.core.utils.ByteUtils;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;


/**
 * @author Cruise.Xu
 * 
 */
public class TextMessageConsumer2 {
	
	private final static Logger logger = LoggerFactory.getLogger(TextMessageConsumer2.class);
	
	@Resource
	private MessageConusmer2<String> messageConusmer2;
	
	@Resource
	private ConsumerConfig consumerConfig;
	
	private String topic;
    
	private Integer partitionsNum;
	
	private ConsumerConnector connector;
	
	public void init() {
		
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		
		connector = Consumer.createJavaConsumerConnector(consumerConfig);
		
		String[] topics = topic.split(",");
		for (int i = 0; i < topics.length; i++) {
			topicCountMap.put(topics[i], partitionsNum);
		}

		Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams 
			= connector.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = new ArrayList<KafkaStream<byte[], byte[]>>();
		for (int i = 0; i < topics.length; i++) {
			streams.addAll(topicMessageStreams.get(topics[i]));
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	    //	    
		final ExecutorService executor = Executors.newFixedThreadPool(partitionsNum * topics.length);
	    for (final KafkaStream<byte[], byte[]> stream : streams) {
	    	executor.submit(new Runnable() {
				public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
					while (it.hasNext()) {
						byte[] message = it.next().message();
						String object = (String)ByteUtils.fromByte(message);
						messageConusmer2.processMessage(object);
					}
                }
            });
	    }
	    	    
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	    	public void run() {
	    		executor.shutdown();
	    	}
	    }));
	}	

	public void setMessageConusmer2(MessageConusmer2<String> messageConusmer2) {
		this.messageConusmer2 = messageConusmer2;
	}
	
	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public void setTopic(String topic) {
		this.topic = topic.trim().replaceAll(" ", "");
	}

	public void setPartitionsNum(Integer partitionsNum) {
		this.partitionsNum = partitionsNum;
	}
	
	public void destroy() {
		if(null != connector)
			connector.shutdown();
	}
	
}
