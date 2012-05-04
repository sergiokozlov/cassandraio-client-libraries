package io.cassandra.sdk.cql;

import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.data.DataMapModel;

import java.util.Map;


@SuppressWarnings("serial")
public class CqlMapModel extends StatusMessageModel {

	private Map<String, DataMapModel> cqlMap;

	public void setCqlMap(Map<String, DataMapModel> cqlMap) {
		this.cqlMap = cqlMap;
	}

	/**
	 * Returns the Data from a CQL Query.  <br/>
	 * The key in the Map is the rowkey for the DataMapModel
	 * @return
	 */
	public Map<String, DataMapModel> getCqlMap() {
		return cqlMap;
	}
	
}
