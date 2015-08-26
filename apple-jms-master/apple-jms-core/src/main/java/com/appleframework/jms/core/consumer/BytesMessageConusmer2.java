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
public class BytesMessageConusmer2 implements MessageListener {
	
	private MessageConusmer2<Object> messageConusmer2;
	
	public void setMessageConusmer2(MessageConusmer2<Object> messageConusmer2) {
		this.messageConusmer2 = messageConusmer2;
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
				messageConusmer2.processMessage(object);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
}
