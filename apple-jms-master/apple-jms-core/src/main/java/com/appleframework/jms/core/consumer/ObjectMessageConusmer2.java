package com.appleframework.jms.core.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;


/**
 * @author xusm
 * 
 */
public class ObjectMessageConusmer2 implements MessageListener {
	
	private MessageConusmer2<Object> messageConusmer2;
	
	public void setMessageConusmer2(MessageConusmer2<Object> messageConusmer2) {
		this.messageConusmer2 = messageConusmer2;
	}

	public void onMessage(Message message) {
		try {
			Object object = ((ObjectMessage) message).getObject();
			messageConusmer2.processMessage(object);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
