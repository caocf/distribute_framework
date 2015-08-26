package com.appleframework.jms.rocketmq.producer;

import java.io.Serializable;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.appleframework.jms.core.exception.MQException;
import com.appleframework.jms.core.utils.ByteUtils;
import com.appleframework.jms.rocketmq.RocketMQProducer;

/**
 * @author Cruise.Xu
 * 
 */
public class RocketMessageProducer2 {

	private RocketMQProducer producer;	

	public void setProducer(RocketMQProducer producer) {
		this.producer = producer;
	}

	public SendResult sendByte(String topic, String tags, String keys, byte[] message) throws MQException {
        Message msg = new Message(topic, tags, keys, message);
        try {
			return producer.send(msg);
		} catch (MQClientException | RemotingException | MQBrokerException
				| InterruptedException e) {
			throw new MQException(e);
		}
	}

	public SendResult sendObject(String topic, String tags, String keys, Serializable message) throws MQException {		
		Message msg = new Message(topic, tags, ByteUtils.toBytes(message));
		try {
			return producer.send(msg);
		} catch (MQClientException | RemotingException | MQBrokerException
				| InterruptedException e) {
			throw new MQException(e);
		}
	}

	public SendResult sendText(String topic, String tags, String keys, String message) throws MQException {		
		Message msg = new Message(topic, tags, ByteUtils.toBytes(message));
		try {
			return producer.send(msg);
		} catch (MQClientException | RemotingException | MQBrokerException
				| InterruptedException e) {
			throw new MQException(e);
		}
	}	

}
