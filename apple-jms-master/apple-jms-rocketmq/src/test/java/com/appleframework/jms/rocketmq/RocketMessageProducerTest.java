package com.appleframework.jms.rocketmq;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.jms.rocketmq.producer.RocketMessageProducer2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-producer.xml" })
public class RocketMessageProducerTest {

	private static Logger logger = Logger.getLogger(RocketMessageProducerTest.class.getName());
    
	@Resource
	private RocketMessageProducer2 messageProducer;

	@Test
	public void testAddOpinion1() {
		try {
			for (int i = 0; i < 10; i++) {
				messageProducer.sendText("xu", "i", i + "", "xuxuxuxuxu" + i);
			}
			logger.error("------------------");
			System.in.read();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
