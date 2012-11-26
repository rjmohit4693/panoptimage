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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.fereor.davdroid.DavDroidConstants;
import org.fereor.davdroid.http.xml.o.Multistatus;
import org.fereor.davdroid.http.xml.o.Response;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

public class XmlMultistatusIterator implements Iterator<Response> {
	/** Parser to read the stream */
	private XmlPullParser parser;
	/** status of reader */
	private boolean status = false;
	/** Current object read */
	private Response current;
	/** error status of the stream parsing */
	private boolean onError;

	/**
	 * Constructor
	 * 
	 * @param data
	 *            data to parse
	 * @param parser
	 *            XML parser to parse data
	 * @throws XmlPullParserException 
	 */
	public XmlMultistatusIterator(byte[] data, XmlPullParser parser) throws XmlPullParserException {
		// prepare parser
		this.onError = false;
		this.parser = parser;
		this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		this.parser.setInput(new ByteArrayInputStream(data), null);
		try {
			// start parsing : detect starting of message
			this.parser.next();
			this.parser.require(XmlPullParser.START_TAG, Multistatus.NAMESPACE,
					Multistatus.TAGNAME);
		} catch (IOException e) {
			this.onError = true;
		}
	}

	@Override
	public boolean hasNext() {
		if (onError)
			return false;
		try {
			// read the next object
			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getName() != null
						&& parser.getEventType() == XmlPullParser.START_TAG) {
					parser.next();
					current = new Response();
					current.readself(parser);
					status = true;
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			Log.d(DavDroidConstants.LOG_TAG, "Unable to get next element", e);
			return false;
		}
	}

	@Override
	public Response next() {
		if (!status) {
			if (!hasNext()) {
				return null;
			}
		}
		return current;
	}

	@Override
	public void remove() {
		// Do nothing
	}
}

