package io.cassandra.sdk.column;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.exception.CassandraIoException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ColumnAPI extends CassandraIoSDK {
	
	public static final String API_CALL = APIConstants.API_COLUMN;

	public ColumnAPI(APIConnection apiConnection, String apiUrl, String token,
			String accountId) {
		super(apiConnection, apiUrl, token, accountId);
		
	}

	public ColumnAPI(String apiUrl, String token,
			String accountId) {
		super(apiUrl, token, accountId);

	}
	
	/**
	 * Creates or updates a new column in a column family <br/>
	 * @param keyspaceName
	 * @param columnFamilyName
	 * @param columnName
	 * @param comparatorType
	 * @param isIndex
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel upsertColumn(String keyspaceName, String columnFamilyName, String columnName, String comparatorType, 
			boolean isIndex) throws CassandraIoException {
		
		try{
			
			String path = pathBuilder(keyspaceName, columnFamilyName, columnName, comparatorType);
			if(isIndex)
				path = path +"?isIndex=true";
			
			JsonObject jso = getJsonResponse(API_CALL, path,
					METH_POST).getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
			
		}catch(Exception e){
			throw new CassandraIoException(e);
		}
		
	}
}
