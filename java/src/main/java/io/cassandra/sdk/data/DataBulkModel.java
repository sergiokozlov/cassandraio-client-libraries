package io.cassandra.sdk.data;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DataBulkModel implements Serializable {
	
	
	private List<DataRowkey> rowkeys;
	
	public DataBulkModel(List<DataRowkey> rowkeys){
		this.rowkeys = rowkeys;
	}

	public void setRowkeys(List<DataRowkey> rowkeys) {
		this.rowkeys = rowkeys;
	}

	public List<DataRowkey> getRowkeys() {
		return rowkeys;
	}
	
	
}
