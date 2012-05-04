package io.cassandra.sdk.counter;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.exception.CassandraIoException;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CounterAPI extends CassandraIoSDK {
	
	public static final String API_CALL = APIConstants.API_COUNTER;

	public CounterAPI(APIConnection apiConnection, String apiUrl, String token,
			String accountId) {
		super(apiConnection, apiUrl, token, accountId);
		// TODO Auto-generated constructor stub
	}

	public CounterAPI(String apiUrl, String token, String accountId) {
		super(apiUrl, token, accountId);
		// TODO Auto-generated constructor stub
	}

	
	private StatusMessageModel adjustCounter(String keyspaceName, String columnFamilyName, 
			String rowKey, String columnName, int value) 
		throws CassandraIoException{
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, rowKey, columnName);
			
			Map<String,String> params = new HashMap<String,String>();
			params.put("count", String.valueOf(value));
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_POST, 
					buildRequestString(params, null)).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}
	
	/**
	 * Decrements a counter
	 * @param rowKey
	 * @param columnName
	 * @param decrementValue
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel decrementCounter(String keyspaceName, String columnFamilyName,
			String rowKey, String columnName, int decrementValue)
		throws CassandraIoException {
		
		if(decrementValue > 0){
			decrementValue = decrementValue * -1;
		}
		return adjustCounter(keyspaceName, columnFamilyName, rowKey, columnName, decrementValue);
		
	}
	
	/**
	 * Increments a counter
	 * @param rowKey
	 * @param columnName
	 * @param incrementValue
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel incrementCounter(String keyspaceName, String columnFamilyName,
			String rowKey, String columnName, int incrementValue)
		throws CassandraIoException {
	
		return adjustCounter(keyspaceName, columnFamilyName, rowKey, columnName, incrementValue);
	}
	
	/**
	 * Returns the count 
	 * @param rowKey
	 * @param columnName
	 * @return
	 * @throws CassandraIoException
	 */
	public DataMapModel getCounter(String keyspaceName, String columnFamilyName,
			String rowKey, String columnName)
		throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, rowKey, columnName);		
	
			JsonObject jso = getJsonResponse(API_CALL, path, METH_GET).getAsJsonObject();
				
			Gson gson = new Gson();
			return gson.fromJson(jso, DataMapModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}

}
