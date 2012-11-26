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
import org.fereor.davdroid.http.xml.i.DavInput;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * Parse a XmlStream into a basic object, as an iterator. Next element in only
 * read when asked by the <code>next()</code> method
 * 
 * @author "arnaud.p.fereor"
 * 
 * @param <T>
 *            return type of Iterator <code>next()</code> method
 */
public class XmlStreamIterator<T extends DavInput> implements Iterator<T> {

	/** Stream to read */
	private byte[] input;
	/** Parser to read the stream */
	private XmlPullParser parser;
	/** status of reader */
	private boolean status = false;
	/** eof of reader */
	private boolean eof = false;
	/** Current object read */
	private T current;

	/**
	 * Constructor
	 * 
	 * @param stream
	 *            stream to read
	 * @param parser
	 *            Xml parser to use for parsing
	 * @throws XmlPullParserException
	 */
	public XmlStreamIterator(byte[] input, XmlPullParser parser)
			throws XmlPullParserException {
		this.input = input;
		this.parser = parser;
		this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		this.parser.setInput(new ByteArrayInputStream(input), null);
	}

	/**
	 * Checks if the stream has another object left in stream
	 */
	public boolean hasNext() {
		// read the next object
//		10-23 20:58:17.407: D/DavDroid:(1195): <D:response>
//		10-23 20:58:17.407: D/DavDroid:(1195): <D:href>/WebDAV/Documents/Proposition%20cciale%20ETRE%20LE%20COACH%20DE%20SON%20EQUIPE-PRINT%20DOC%20220512.pdf</D:href>
//		10-23 20:58:17.407: D/DavDroid:(1195): <D:propstat>
//		10-23 20:58:17.407: D/DavDroid:(1195): <D:prop>
//		10-23 20:58:17.407: D/DavDroid:(1195): </D:prop>
//		10-23 20:58:17.407: D/DavDroid:(1195): <D:status>HTTP/1.1 200 OK</D:status>
//		10-23 20:58:17.407: D/DavDroid:(1195): </D:propstat>
//		10-23 20:58:17.407: D/DavDroid:(1195): </D:response>

		try {
			int last = parser.next();
			while (last != XmlPullParser.END_TAG) {
				if (parser.getEventType() == XmlPullParser.START_TAG) {
					// create new object
					String tag = parser.getName();
					String namespace = parser.getNamespace();
					DavInput cur = readObject(namespace, tag);
				}
			}
			return true;
		} catch (XmlPullParserException e) {
			Log.d(DavDroidConstants.LOG_TAG, "Unable to parse string", e);
			return false;
		} catch (IOException e) {
			Log.d(DavDroidConstants.LOG_TAG, "Unable to parse string", e);
			return false;
		}

	}

	/**
	 * Returns the last object read
	 */
	public T next() {
		if (!status) {
			if (!hasNext()) {
				return null;
			}
		}
		return current;
	}

	public void remove() {
		// Do nothing
	}

	private DavInput readObject(String ns, String tag)
			throws XmlPullParserException, IOException {
		parser.require(XmlPullParser.START_TAG, ns, tag);
		String title = readText(parser);
		parser.require(XmlPullParser.END_TAG, ns, "title");
		return null;
	}

	private String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}
}
