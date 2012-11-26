package org.fereor.panoptimage.exception;

public class PanoptimageNoNetworkException extends PanoptimageException {
	/** UID for the class */
	private static final long serialVersionUID = -5617417945877133824L;
	private String server;

	/**
	 * Default constructor
	 * 
	 * @param location
	 */
	public PanoptimageNoNetworkException(String server) {
		this.setServer(server);
	}

	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}
}
