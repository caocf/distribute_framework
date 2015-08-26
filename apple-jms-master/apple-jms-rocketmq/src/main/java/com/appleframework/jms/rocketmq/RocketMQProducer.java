package com.appleframework.jms.rocketmq;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class RocketMQProducer extends DefaultMQProducer {
	
	private String namesrvAddr;
	
	private String producerGroup;

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public void setProducerGroup(String producerGroup) {
		this.producerGroup = producerGroup;
	}

	public void init() {
		try {
			super.setProducerGroup(producerGroup);
			super.setNamesrvAddr(namesrvAddr);
			this.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		this.shutdown();
	}

}
