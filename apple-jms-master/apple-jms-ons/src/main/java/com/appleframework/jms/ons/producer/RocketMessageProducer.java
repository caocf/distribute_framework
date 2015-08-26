package com.appleframework.jms.ons.producer;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.JmsException;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.appleframework.jms.core.exception.MQException;
import com.appleframework.jms.core.producer.MessageProducer;
import com.appleframework.jms.core.utils.ByteUtils;
import com.appleframework.jms.ons.RocketMQProducer;

/**
 * @author Cruise.Xu
 * 
 */
public class RocketMessageProducer implements MessageProducer {
	
	private final static Logger logger = LoggerFactory.getLogger(RocketMessageProducer.class);

	private RocketMQProducer producer;
	
	private String topic;
	
	private String tags;
	
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public void sendByte(byte[] message) throws JmsException {
        Message msg = new Message(topic, tags, message);
        try {
			SendResult result = producer.send(msg);
			logger.info("msgId=" + result.getMessageId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MQException(e);
		}  
	}

	@Override
	public void sendObject(Serializable message) throws JmsException {		
		Message msg = new Message(topic, tags, ByteUtils.toBytes(message));
        try {
			SendResult result = producer.send(msg);
			logger.info("msgId=" + result.getMessageId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MQException(e);
		}  
	}

	@Override
	public void sendText(String message) throws JmsException {		
		Message msg = new Message(topic, tags, ByteUtils.toBytes(message));
        try {
			SendResult result = producer.send(msg);
			logger.info("msgId=" + result.getMessageId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new MQException(e);
		}
	}

	public void setProducer(RocketMQProducer producer) {
		this.producer = producer;
	}
	

}
