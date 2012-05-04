package io.cassandra.sdk.columnfamily;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.exception.CassandraIoException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ColumnFamilyAPI extends CassandraIoSDK {
	
	public static final String API_CALL = APIConstants.API_COLUMNFAMILY;

	public ColumnFamilyAPI(APIConnection apiConnection, String apiUrl,
			String token, String accountId) {
		super(apiConnection, apiUrl, token, accountId);
		
	}

	public ColumnFamilyAPI(String apiUrl,
			String token, String accountId) {
		super(apiUrl, token, accountId);
		
	}
	
	/**
	 * Creates a new column family if it doesn't already exist
	 * 
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param comparatorType
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel createColumnFamily(String keyspaceName, String columnFamilyName, String comparatorType)
		throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, comparatorType);
			
			JsonObject jso = getJsonResponse(API_CALL, path,
					METH_POST).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}
	
	/**
	 * Get metadata about a column family
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @return
	 * @throws CassandraIoException
	 */
	public ColumnFamilyModel getColumnFamily(String keyspaceName, String columnFamilyName) 
			throws CassandraIoException {
		
		Gson gson = new Gson();
		JsonObject jso = null;
		try{
			String path = pathBuilder(keyspaceName, columnFamilyName);
			
			jso = getJsonResponse(API_CALL, path, METH_GET)
				.getAsJsonObject();
			
			log.info("Returned: "+ jso.toString());
			
			return gson.fromJson(jso, ColumnFamilyModel.class);
			
		}catch(Exception e){
			
			throw new CassandraIoException(e);
			
		}
	}
	
	/**
	 * Get metadata about all my column families
	 * @param keyspaceName
	 * @return
	 * @throws CassandraIoException
	 */
	public ColumnFamiliesModel getColumnFamilies(String keyspaceName) throws CassandraIoException {
		
		Gson gson = new Gson();
		JsonObject jso = null;
		try{
			
			jso = getJsonResponse(API_CALL, keyspaceName, METH_GET)
				.getAsJsonObject();
			
			log.info("Returned: "+ jso.toString());
			
			return gson.fromJson(jso, ColumnFamiliesModel.class);
			
		}catch(Exception e){
			
			throw new CassandraIoException(e);
			
		}
	}
	
	/**
	 * Deletes a given column family
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel deleteColumnFamily(String keyspaceName, String columnFamilyName) 
			throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName);
			
			JsonObject jso = getJsonResponse(API_CALL, path,
					METH_DELETE).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
	}
}
