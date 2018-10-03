package com.cg.obs.exception;

public class JDBCConnectionError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JDBCConnectionError(String message)
	{
		super(message);
	}
	
}
