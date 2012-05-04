package io.cassandra.sdk.data;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataRowkey implements Serializable {
	
	private String rowkey;
	private List<DataColumn> columns;
	
	public DataRowkey(String rowkey, List<DataColumn> columns){
		this.rowkey = rowkey;
		this.columns = columns;
	}
	
	public String getRowkey() {
		return rowkey;
	}
	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	public List<DataColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<DataColumn> columns) {
		this.columns = columns;
	}
	
	

}
