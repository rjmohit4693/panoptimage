package org.fereor.panoptimage.exception;

public class PanoptimageFileNotFoundException extends PanoptimageException {

	/** location of error */
	private String location;

	/**
	 * Default constructor
	 * 
	 * @param location
	 */
	public PanoptimageFileNotFoundException(String location) {
		this.setLocation(location);
	}

	public String getLocation() {
		return location;
	}

	private void setLocation(String location) {
		this.location = location;
	}

	/** serial ID */
	private static final long serialVersionUID = 6152495703340008906L;

}
