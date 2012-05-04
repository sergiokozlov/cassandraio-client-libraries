package io.cassandra.sdk.conn;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface APIConnection {
	
	public HttpURLConnection getAPIConnection(URL URLSource, String method, String params, String username, String password) throws IOException;


}
