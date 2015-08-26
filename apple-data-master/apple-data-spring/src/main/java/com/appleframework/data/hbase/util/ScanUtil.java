package com.appleframework.data.hbase.util;

import org.apache.hadoop.hbase.client.Scan;

import com.appleframework.data.hbase.client.RowKey;

public class ScanUtil {

	// 获取扫描器对象
	public static Scan getScan(RowKey startRow, RowKey stopRow) {
		Scan scan = new Scan();
		scan.setStartRow(startRow.toBytes());
		scan.setStopRow(stopRow.toBytes());
		return scan;
	}
}
