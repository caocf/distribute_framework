package com.appleframework.jms.kafka.partitions;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * @author caicq
 * 
 */
public class SimplePartitioner implements Partitioner {

	public SimplePartitioner(VerifiableProperties props) {
	}

	@Override
	public int partition(Object key, int numPartitions) {
		long partitionKey = Long.parseLong(key.toString());
		int partition = (int) (partitionKey % numPartitions);
		return partition;
	}

}
