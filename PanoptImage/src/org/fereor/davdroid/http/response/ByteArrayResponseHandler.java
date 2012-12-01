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
import org.fereor.davdroid.DavDroidListener;
import org.fereor.davdroid.exception.DavDroidException;

/**
 * Handler for a stream output
 * 
 * @author "arnaud.p.fereor"
 */
public class ByteArrayResponseHandler extends BasicResponseHandler<byte[]> {
	/** listener */
	private DavDroidListener<Long> lsn;
	private Long progress = 0l;

	/**
	 * Default constructor
	 * 
	 * @param input input data
	 */
	public ByteArrayResponseHandler(boolean buf, DavDroidListener<Long> lsn) {
		super();
		this.lsn = lsn;
	}

	/**
	 * Handles the response
	 */
	public byte[] handleResponse(HttpResponse response) throws DavDroidException, IOException {
		// check the response return code
		super.validateResponse(response);

		// Process the response from the server.
		HttpEntity entity = response.getEntity();
		StatusLine statusLine = response.getStatusLine();
		if (entity == null) {
			throw new DavDroidException("Unknown entity", statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}
		// if no buffer used

		// get content
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = entity.getContent();
		Long length = entity.getContentLength();
		byte[] buf = new byte[1024];
		int cnt;
		float step = 0;
		while ((cnt = in.read(buf)) > 0) {
			baos.write(buf, 0, cnt);
			progress += cnt;
			float val = ((float) progress) / ((float) length) * lsn.nbSteps();
			if (val >= step) {
				step = val;
				lsn.onProgress(progress, length);
			}
		}
		in.close();

		// create buffer object with results
		return baos.toByteArray();
	}
}
