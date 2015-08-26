package com.appleframework.jms.ons.consumer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.appleframework.jms.core.consumer.MessageConusmer;
import com.appleframework.jms.ons.RocketMQPushConsumer;


/**
 * @author Cruise.Xu
 * 
 */
public abstract class BytesMessageConsumer extends MessageConusmer {
	
	private final static Logger logger = LoggerFactory.getLogger(BytesMessageConsumer.class);
	
	private RocketMQPushConsumer consumer;
	
	private String topic;
	
	private String tags;
	
	protected byte[] message;

	public abstract void processMessage();
	
	public void setConsumer(RocketMQPushConsumer consumer) {
		this.consumer = consumer;
	}
		
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	protected void init() throws MQClientException {
        consumer.subscribe(topic, tags);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);  
        consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext Context) {
                    Message msg = list.get(0);
                    logger.info(msg.toString());
                    message = msg.getBody();
                    processMessage();
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            }
        );
        consumer.start();
	}

	public byte[] getMessage() {
		return message;
	}

	public void setMessage(byte[] message) {
		this.message = message;
	}
}
