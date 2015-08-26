package com.appleframework.jms.rabbit.producer;

import java.io.Serializable;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.jms.JmsException;

import com.appleframework.jms.core.producer.MessageProducer;

/**
 * Hello world!
 *
 */
public class RabbitMessageProducer implements MessageProducer {

	private RabbitTemplate rabbitTemplate;
	
	private String topic;

	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void sendByte(byte[] message) throws JmsException {
		rabbitTemplate.convertAndSend(topic, message);
	}

	@Override
	public void sendObject(Serializable message) throws JmsException {
		rabbitTemplate.convertAndSend(topic, message);
	}

	@Override
	public void sendText(String message) throws JmsException {
		rabbitTemplate.convertAndSend(topic, message);
	}	

}