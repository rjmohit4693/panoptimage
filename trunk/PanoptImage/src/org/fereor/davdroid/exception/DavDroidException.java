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

package org.fereor.davdroid.exception;

import java.io.IOException;

/**
 * Default Exception for DavDroid package
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class DavDroidException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8682509371587541377L;
	
	private String message;
	private String reasonPhrase;
	private int statusCode;

	/**
	 * Constructor of default exception
	 * 
	 * @param string
	 * @param statusCode
	 * @param reasonPhrase
	 */
	public DavDroidException(String message, int statusCode, String reasonPhrase) {
		this.message = message;
		this.statusCode = statusCode;
		this.reasonPhrase = reasonPhrase;
	}

	public DavDroidException(String message) {
		this.message = message;
	}

	@Override
	public Throwable getCause() {
		return super.getCause();
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		return res.append(message).append(" [").append(statusCode).append("]")
				.append(": ").append(reasonPhrase).toString();
	}

}
