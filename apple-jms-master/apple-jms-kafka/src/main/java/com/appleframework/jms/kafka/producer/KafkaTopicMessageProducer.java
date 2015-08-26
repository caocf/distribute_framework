package com.appleframework.jms.kafka.producer;

import java.io.Serializable;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.springframework.jms.JmsException;

import com.appleframework.jms.core.producer.TopicMessageProducer;
import com.appleframework.jms.core.utils.ByteUtils;

/**
 * @author Cruise.Xu
 * 
 */
public class KafkaTopicMessageProducer implements TopicMessageProducer {

	private Producer<String, byte[]> producer;
	
	public void setProducer(Producer<String, byte[]> producer) {
		this.producer = producer;
	}
	
	public void sendByte(String topic, byte[] message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, message);
		producer.send(producerData);
	}

	@Override
	public void sendObject(String topic, Serializable message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, ByteUtils.toBytes(message));
		producer.send(producerData);
	}

	@Override
	public void sendText(String topic, String message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, ByteUtils.toBytes(message));
		producer.send(producerData);
	}	

}
