// This file is part of DavDroid.
//
// DavDroid is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// DavDroid is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with DavDroid.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.davdroid.http.util;

import org.apache.http.StatusLine;

/**
 * Class to store the response of a HTTP request in a buffer
 * @author "arnaud.p.fereor"
 *
 */
public class ResponseBuffer {
	/** byte buffer */
	private byte[] buffer;
	/** status of answer */
	private StatusLine status;

	/**
	 * Constructor of object
	 * @param byteArray buffer to store
	 * @param statusLine status line of response
	 */
	public ResponseBuffer(byte[] byteArray, StatusLine statusLine) {
		buffer = byteArray;
		status = statusLine;
	}

	/**
	 * getter for buffer
	 * @return buffer
	 */
	public byte[] getBuffer() {
		return buffer;
	}

	/**
	 * setter for buffer
	 * @param buffer buffer to store
	 */
	public void setBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * getter for status
	 * @return status
	 */
	public StatusLine getStatus() {
		return status;
	}

	/**
	 * setter for status
	 * @param status status to store
	 */
	public void setStatus(StatusLine status) {
		this.status = status;
	}
}


