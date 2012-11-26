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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.http.util.ResponseBuffer;

/**
 * Handler for a byte array output
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class BufferResponseHandler extends BasicResponseHandler<ResponseBuffer> {

	/**
	 * Default constructor
	 * 
	 * @param input
	 *            input data
	 */
	public BufferResponseHandler() {
		super();
	}

	/**
	 * Handles the response
	 */
	public ResponseBuffer handleResponse(HttpResponse response)
			throws DavDroidException, IOException {
		// check the response return code
		super.validateResponse(response);

		// Process the response from the server.
		HttpEntity entity = response.getEntity();
		StatusLine statusLine = response.getStatusLine();
		if (entity == null) {
			// create buffer object with no result
			return new ResponseBuffer(null, statusLine);
		}

		// get content
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = entity.getContent();
		byte[] buf = new byte[1024];
		int cnt;
		while ((cnt = in.read(buf)) > 0) {
			baos.write(buf, 0, cnt);
		}
		in.close();

		// create buffer object with results
		return new ResponseBuffer(baos.toByteArray(), statusLine);
	}

}