package com.appleframework.jms.ons;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

public class RocketMQPushConsumer extends DefaultMQPushConsumer {

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
		this.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
	}

}
