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

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.fereor.davdroid.exception.DavDroidException;

/**
 * Handler for a multi-status response (code 207)
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class ExistsResponseHandler extends
		BasicResponseHandler<Boolean> {
	/**
	 * Handles the response
	 */
	public Boolean handleResponse(HttpResponse response)
			throws DavDroidException, IOException {
		// Check the status code
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES)
		{
			return true;
		}
		return false;
	}
}