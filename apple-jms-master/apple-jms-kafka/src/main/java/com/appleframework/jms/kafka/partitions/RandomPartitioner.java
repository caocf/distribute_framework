package com.appleframework.jms.kafka.partitions;

import com.appleframework.jms.kafka.utils.RandomUtility;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * @author cruise.xu
 * 
 */
public class RandomPartitioner implements Partitioner {

	public RandomPartitioner(VerifiableProperties props) {
	}

	@Override
	public int partition(Object key, int numPartitions) {
		int partition = RandomUtility.genRandom(0, numPartitions);
		return partition;
	}

}
