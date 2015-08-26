package com.appleframework.jms.kafka.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import com.appleframework.jms.core.consumer.MessageConusmer;
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
public abstract class ObjectMessageConsumer extends MessageConusmer {
	
	@Resource
	private ConsumerConfig consumerConfig;
	
	private String topic;
    
	private Integer partitionsNum;
	
	private ConsumerConnector connector;
	protected Object message;

	public abstract void processMessage();
			
	protected void init() {
		
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
		
	    //	    
		final ExecutorService executor = Executors.newFixedThreadPool(partitionsNum * topics.length);
	    for (final KafkaStream<byte[], byte[]> stream : streams) {
	    	executor.submit(new Runnable() {
				public void run() {
                    ConsumerIterator<byte[], byte[]> it = stream.iterator();
					while (it.hasNext()) {
						byte[] message = it.next().message();
						Object object = ByteUtils.fromByte(message);
						setMessage(object);
						processMessage();
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

	public void setConsumerConfig(ConsumerConfig consumerConfig) {
		this.consumerConfig = consumerConfig;
	}

	public void setTopic(String topic) {
		this.topic = topic.trim().replaceAll(" ", "");
	}

	public void setPartitionsNum(Integer partitionsNum) {
		this.partitionsNum = partitionsNum;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	public void destroy() {
		if(null != connector)
			connector.shutdown();
	}
}
