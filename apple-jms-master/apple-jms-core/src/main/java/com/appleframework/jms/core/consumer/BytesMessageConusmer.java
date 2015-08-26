package com.appleframework.jms.core.consumer;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import com.appleframework.jms.core.utils.ByteUtils;

/**
 * @author xusm
 * 
 */
public abstract class BytesMessageConusmer extends MessageConusmer implements MessageListener {
	
	protected Object message;
	
	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public void onMessage(Message message) {
		try {
			Object object = null;
			if (message instanceof BytesMessage) {
				// 两次强转，获得消息中的主体对象字节数组流
				byte[] block = new byte[1024];  
				int count = ((BytesMessage) message).readBytes(block);
				try {
					if(count > -1)
						object = ByteUtils.fromByte(block);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			setMessage(object);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		processMessage();
	}
	
	public abstract void processMessage();	

}
