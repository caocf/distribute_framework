package com.appleframework.jms.core.creator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import com.appleframework.jms.core.utils.ByteUtils;

@Component("objectMessageConverter")
public class ObjectMessageConverter implements MessageConverter {

	// 从消息中取出对象
	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		Object object = null;
		if (message instanceof ObjectMessage) {
			// 两次强转，获得消息中的主体对象字节数组流
			byte[] obj = (byte[]) ((ObjectMessage) message).getObject();
			try {
				object = ByteUtils.fromByte(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return object;
	}

	// 将对象转换成消息
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		ObjectMessage objectMessage = session.createObjectMessage();
		try {
			byte[] objMessage = ByteUtils.toBytes(object);// 字节数组输出流转成字节数组
			objectMessage.setObject(objMessage);// 将字节数组填充到消息中作为消息主体
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectMessage;
	}

}