package org.fereor.panoptimage.dao.repository;

public class ByteRepositoryContent extends RepositoryContent {

	/** byte buffer  */
	private byte[] bytes;

	/**
	 * Default constructor
	 * @param array array of bytes
	 */
	public ByteRepositoryContent(byte[] array) {
		this.setBytes(array);
	}

	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
