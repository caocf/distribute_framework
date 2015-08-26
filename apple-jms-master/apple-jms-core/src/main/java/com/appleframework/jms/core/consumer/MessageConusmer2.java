package com.appleframework.jms.core.consumer;

/**
 * @author cruise.xu
 * 
 */
public interface MessageConusmer2<Message> {
	
	public void processMessage(Message message);

}
