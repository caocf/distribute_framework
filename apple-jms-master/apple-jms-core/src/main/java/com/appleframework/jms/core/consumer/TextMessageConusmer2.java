package com.appleframework.jms.core.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


/**
 * @author xusm
 * 
 */
public class TextMessageConusmer2 implements MessageListener {
	
	private MessageConusmer2<String> messageConusmer2;
	
	public void setMessageConusmer2(MessageConusmer2<String> messageConusmer2) {
		this.messageConusmer2 = messageConusmer2;
	}

	public void onMessage(Message message) {
		try {
			String object = ((TextMessage) message).getText();
			messageConusmer2.processMessage(object);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
