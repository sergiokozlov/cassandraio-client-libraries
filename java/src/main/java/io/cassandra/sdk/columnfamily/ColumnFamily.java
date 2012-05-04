package io.cassandra.sdk.columnfamily;

import io.cassandra.sdk.StatusMessageModel;

@SuppressWarnings("serial")
public class ColumnFamily extends StatusMessageModel {

	private String sortedby;
	private String name;
	
	
	public String getSortedby() {
		return sortedby;
	}
	public void setSortedby(String sortedby) {
		this.sortedby = sortedby;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
