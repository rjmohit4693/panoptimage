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
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;

/**
 * Handler to return the status code of the request, and nothing more
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class StatusCodeResponseHandler extends BasicResponseHandler<StatusLine> {

	/**
	 * Returns the status code of the request
	 */
	public StatusLine handleResponse(HttpResponse response)
			throws ClientProtocolException, IOException {
		return response.getStatusLine();
	}
}
