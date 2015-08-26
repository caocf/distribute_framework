package com.appleframework.jms.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPullConsumer;

public class RocketMQPullConsumer extends DefaultMQPullConsumer {

	private String namesrvAddr;

	private String consumerGroup;

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public void close() {
		this.shutdown();
	}

	public void init() {
		super.setNamesrvAddr(namesrvAddr);
		super.setConsumerGroup(consumerGroup);
	}

}
