package io.cassandra.sdk.exception;


@SuppressWarnings("serial")
public class CassandraIoException extends Exception {

	public CassandraIoException(Exception e) {
		super(e);
	}

	public CassandraIoException(String message, Exception e){
		super(message, e);
	}

}
