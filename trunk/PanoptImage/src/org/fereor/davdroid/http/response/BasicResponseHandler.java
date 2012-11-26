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

package org.fereor.davdroid.http.response;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.exception.DavDroidStatusException;

/**
 * Basic response handler which takes an url for documentation.
 * 
 * @author "arnaud.p.fereor"
 * 
 * @param <T>
 */
public abstract class BasicResponseHandler<T> implements ResponseHandler<T> {
	/**
	 * Checks the response for a statuscode
	 * 
	 * @param response
	 *            to check
	 * @throws DavDroidException when the status code is not acceptable.
	 *             
	 */
	protected void validateResponse(HttpResponse response) throws DavDroidException {
		StatusLine statusLine = response.getStatusLine();
		int statusCode = statusLine.getStatusCode();
		if (statusCode >= HttpStatus.SC_OK
				&& statusCode < HttpStatus.SC_MULTIPLE_CHOICES) {
			return;
		}
		if (statusCode == HttpStatus.SC_FORBIDDEN) {
			throw new DavDroidException("Access is forbidden",
					statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		throw new DavDroidStatusException("Unexpected response",
				statusLine.getStatusCode(), statusLine.getReasonPhrase());
	}
}