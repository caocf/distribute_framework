package com.appleframework.jms.core.producer;

import java.io.Serializable;

import org.springframework.jms.JmsException;

/**
 * 队列消息发送接口
 * @author cruise.xu
 *
 */
public interface MessageProducer {
	
	/**
	 * 发送对象信息
	 * @param code
	 */
	public void sendObject(Serializable message) throws JmsException;
	
	/**
	 * 发送字符串
	 * @param code
	 */
	public void sendText(String message) throws JmsException;
	
	/**
	 * 发送比特流
	 * @param code
	 */
	public void sendByte(byte[] message) throws JmsException;
	
}