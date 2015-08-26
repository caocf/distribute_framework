package com.appleframework.jms.rocketmq;

import com.appleframework.jms.rocketmq.consumer.ObjectMessageConsumer;

/**
 * @author Cruise.Xu
 * 
 */
public class RocketMessageConsumerTest extends ObjectMessageConsumer {

	@Override
	public void processMessage() {
		System.out.println(message);
		
	}
	
}
