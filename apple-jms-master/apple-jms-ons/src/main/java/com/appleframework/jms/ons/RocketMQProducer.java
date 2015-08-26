package com.appleframework.jms.ons;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;

public class RocketMQProducer {
	
	private Producer producer;
	
	private Properties properties;

	public void init() {
		producer = ONSFactory.createProducer(properties);
		producer.start();
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Producer getProducer() {
		return producer;
	}
	
	public void close() {
		producer.shutdown();
	}
	
	public SendResult send(Message message) {
		return producer.send(message);
	}
	
	public void sendOneway(Message message) {
		producer.sendOneway(message);
	}

}
