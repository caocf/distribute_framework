package com.appleframework.jms.core.consumer;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author xusm
 * 
 */
public abstract class JmsMessageConusmer extends MessageConusmer implements MessageListener {
	
	protected Message message;
	
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void onMessage(Message message) {
		setMessage(message);
		processMessage();
	}
	
	public abstract void processMessage();

}
