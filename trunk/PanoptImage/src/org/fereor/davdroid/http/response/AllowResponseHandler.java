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
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.fereor.davdroid.DavDroidConstants;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.http.enums.HttpMethod;

import android.util.Log;

/**
 * Handler for a allow response code 
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class AllowResponseHandler extends
		BasicResponseHandler<List<HttpMethod>> {
	/**
	 * Handles the response
	 */
	public List<HttpMethod> handleResponse(HttpResponse response)
			throws DavDroidException, IOException {
		// validate the status code
		super.validateResponse(response);

		// Process the response from the server.
		HttpEntity entity = response.getEntity();
		StatusLine statusLine = response.getStatusLine();
		Header[] allowHeader = response.getHeaders("Allow");
		if (entity == null) {
			throw new DavDroidException("No entity found in response",
					statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		return getAllows(allowHeader);
	}

	/**
	 * Method to get the content of the Allows response
	 * 
	 * @param headers
	 *            The headers to parse
	 * @return List<HttpMethod> element parsed from the stream
	 * @throws IOException
	 *             When there is an error
	 */
	protected List<HttpMethod> getAllows(Header[] headers) throws IOException {
		Log.d(DavDroidConstants.LOG_TAG, "getAllows");
		// Analyze the response from the server
		String methods = headers[0].getValue();
		if(methods == null)
		{
			throw new DavDroidException("Allow header not found");
		}
		return HttpMethod.parseStringArray(methods, ",");
	}
}