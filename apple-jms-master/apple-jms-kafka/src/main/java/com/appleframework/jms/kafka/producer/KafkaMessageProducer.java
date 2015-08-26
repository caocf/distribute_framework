package com.appleframework.jms.kafka.producer;

import java.io.Serializable;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;

import org.springframework.jms.JmsException;

import com.appleframework.jms.core.producer.MessageProducer;
import com.appleframework.jms.core.utils.ByteUtils;

/**
 * @author Cruise.Xu
 * 
 */
public class KafkaMessageProducer implements MessageProducer {

	private Producer<String, byte[]> producer;
	
	private String topic;

	public void setProducer(Producer<String, byte[]> producer) {
		this.producer = producer;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void sendByte(byte[] message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, message);
		producer.send(producerData);
	}

	@Override
	public void sendObject(Serializable message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, ByteUtils.toBytes(message));
		producer.send(producerData);
	}

	@Override
	public void sendText(String message) throws JmsException {
		KeyedMessage<String, byte[]> producerData 
			= new KeyedMessage<String, byte[]>(topic, ByteUtils.toBytes(message));
		producer.send(producerData);
	}	

}
