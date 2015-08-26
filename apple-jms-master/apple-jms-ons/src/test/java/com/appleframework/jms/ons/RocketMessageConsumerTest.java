package com.appleframework.jms.ons;

import com.appleframework.jms.ons.consumer.ObjectMessageConsumer;

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
