package com.appleframework.jms.core.consumer;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author xusm
 * 
 */
public abstract class JmsMessageConusmer2 implements MessageListener {
	
	private MessageConusmer2<Message> messageConusmer2;
	
	public void setMessageConusmer2(MessageConusmer2<Message> messageConusmer2) {
		this.messageConusmer2 = messageConusmer2;
	}

	public void onMessage(Message message) {
		messageConusmer2.processMessage(message);
	}
	
}
