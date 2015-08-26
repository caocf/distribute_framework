package com.appleframework.jms.core.creator;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.MessageCreator;

public class JmsByteMessageCreator implements MessageCreator {
	
	private byte[] message;

	public JmsByteMessageCreator(byte[] message) {
		this.message = message;
	}
	
	public Message createMessage(Session session) throws JMSException {
		BytesMessage bytesMessage = session.createBytesMessage();
		try {
			bytesMessage.writeBytes(message);// 将字节数组填充到消息中作为消息主体
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytesMessage;
	}
}
