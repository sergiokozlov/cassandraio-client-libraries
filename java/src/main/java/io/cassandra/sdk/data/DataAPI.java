package io.cassandra.sdk.data;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.exception.CassandraIoException;

import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DataAPI extends CassandraIoSDK {
	
	public static final String API_CALL = APIConstants.API_DATA;

	public DataAPI(APIConnection apiConnection, String apiUrl, String token,
			String accountId) {
		super(apiConnection, apiUrl, token, accountId);
		
	}

	public DataAPI(String apiUrl, String token,
			String accountId) {
		super(apiUrl, token, accountId);
	}
	
	/**
	 * Upserts data into a given row. <br/> 
	 * 'params' would be the columnName and values for the row.<br/>
	 * 
	 * 'ttlSeconds' is the data expiration time
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param rowKey
	 * @param params
	 * @param realtime
	 * @param ttlSeconds
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel postData(String keyspaceName, String columnFamilyName, String rowKey, Map<String,String> params,
			int ttlSeconds) throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, rowKey);
			if(ttlSeconds > 0)
				path = path + "?ttl="+ ttlSeconds;
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_POST,
					buildRequestString(params, null)).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}
	
	/**
	 * Upserts data using a DataBulkModel object
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param dataBulk
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel postBulkData(String keyspaceName, String columnFamilyName, DataBulkModel dataBulk) 
		throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName);
			
			Gson gson = new Gson();
			String json = gson.toJson(dataBulk);
			
			System.out.println("Posting JSON: "+ json);
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_POST,
					json).getAsJsonObject();
			
			
			return gson.fromJson(jso, StatusMessageModel.class);
		
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}

	/**
	 * Returns a DataMapModel object of key value pairs
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param rowKey
	 * @param limit
	 * @param fromKey
	 * @return
	 * @throws CassandraIoException
	 */
	public DataMapModel getData(String keyspaceName, String columnFamilyName, String rowKey, int limit, String fromKey)
		throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, rowKey);
			boolean hasLimit = false;
			if(limit > 0)
				hasLimit = true;
			
			if(hasLimit){
				path = path + "?limit="+ limit;
				if(fromKey != null)
					path = path + "&fromKey="+ fromKey;
			}else{
				if(fromKey != null)
					path = path + "?fromKey="+ fromKey;
			}
			
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_GET).getAsJsonObject();
			
			
			Gson gson = new Gson();
			return gson.fromJson(jso, DataMapModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}
	
	/**
	 * Deletes a column of data from a row
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param rowKey
	 * @param columnName
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel deleteData(String keyspaceName, String columnFamilyName, String rowKey, String columnName)
		throws CassandraIoException {
		
		try {
			
			String path = pathBuilder(keyspaceName, columnFamilyName, rowKey, columnName);
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_DELETE).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
	}
	
	/**
	 * Deletes a row form a column family
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param rowKey
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel deleteData(String keyspaceName, String columnFamilyName, String rowKey)
		throws CassandraIoException {
		
		return deleteData(keyspaceName, columnFamilyName, rowKey, null);
	}
}
