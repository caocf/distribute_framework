package com.appleframework.jms.rocketmq;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.jms.rocketmq.producer.RocketMessageProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-consumer.xml" })
public class RocketMessageConsumerTest2 {
    
	@Test
	public void testAddOpinion1() {
		try {
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
