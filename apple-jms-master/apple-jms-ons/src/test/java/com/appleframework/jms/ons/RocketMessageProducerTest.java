package com.appleframework.jms.ons;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.appleframework.jms.ons.producer.RocketMessageProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/spring-producer.xml" })
public class RocketMessageProducerTest {

	private static Logger logger = Logger.getLogger(RocketMessageProducerTest.class.getName());
    
	@Resource
	private RocketMessageProducer messageProducer;

	@Test
	public void testAddOpinion1() {
		try {
			for (int i = 0; i < 10000; i++) {
				messageProducer.sendText("xuxuxuxuxu" + i);
				logger.error("send--->>>>" + i);
			}
			logger.error("------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
