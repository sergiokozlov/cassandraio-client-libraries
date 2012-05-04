package io.cassandra.sdk.keyspace;

import io.cassandra.sdk.CassandraIoSDK;
import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.conn.APIConnection;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.exception.CassandraIoException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class KeyspaceAPI extends CassandraIoSDK {

	public static final String API_CALL = APIConstants.API_KEYSPACE;

	public KeyspaceAPI(APIConnection apiConnection, String apiUrl,
			String token, String accountId) {
		super(apiConnection, apiUrl, token, accountId);
	}

	public KeyspaceAPI(String apiUrl, String token, String accountId) {
		super(apiUrl, token, accountId);
	}
	
	/**
	 * Creates a keyspace with the given name
	 * @param keyspaceName
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel createKeyspace(String keyspaceName)
			throws CassandraIoException {
		try {
			String path = pathBuilder(keyspaceName);
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_POST)
					.getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		} catch (Exception e) {
			throw new CassandraIoException(e);
		}
	}

	/**
	 * Deletes a keyspace with the given name
	 * @param keyspaceName
	 * @return
	 * @throws CassandraIoException
	 */
	public StatusMessageModel deleteKeyspace(String keyspaceName)
		throws CassandraIoException {
		
		try {
			String path = pathBuilder(keyspaceName);
			
			JsonObject jso = getJsonResponse(API_CALL, path, METH_DELETE)
					.getAsJsonObject();
			
			Gson gson = new Gson();
			return gson.fromJson(jso, StatusMessageModel.class);
			
		} catch (Exception e) {
			throw new CassandraIoException(e);
		}
	}
}
