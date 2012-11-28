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
import org.fereor.davdroid.DavDroidListener;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * Parse a XmlStream into a String to match the key passed as argument. XML Stream in only read when asked by the
 * <code>next()</code> method
 * 
 * @author "arnaud.p.fereor"
 * @param <T> return type of Iterator <code>next()</code> method
 */
public class XmlRegexpIterator implements Iterator<String> {

	/** Parser to read the stream */
	private XmlPullParser parser;
	/** status of reader */
	private boolean status = false;
	/** Current object read */
	private String current;
	/** error status of the stream parsing */
	private boolean onError;
	/** Name space of the root element */
	private String rootnamespace;
	/** Name of root element */
	private String rootname;
	/** String used for integration */
	private String key;
	/** Regular expression for filter on search */
	private String regexp;

	/**
	 * Constructor
	 * 
	 * @param stream stream to read
	 * @param parser Xml parser to use for parsing
	 * @throws XmlPullParserException
	 */
	public XmlRegexpIterator(byte[] input, XmlPullParser parser, String key, String regexp)
			throws XmlPullParserException {
		this.onError = false;
		this.parser = parser;
		this.key = key;
		this.regexp = regexp;
		this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		this.parser.setInput(new ByteArrayInputStream(input), null);
		try {
			this.parser.nextTag();
			this.parser.require(XmlPullParser.START_TAG, null, null);
			this.rootnamespace = this.parser.getNamespace();
			this.rootname = this.parser.getName();
		} catch (IOException e) {
			this.onError = true;
		}
	}

	/**
	 * Checks if the stream has another object left in stream
	 */
	public boolean hasNext() {
		if (onError) {
			return false;
		}
		try {
			// read the next object
			while (parser.next() != XmlPullParser.END_DOCUMENT) {
				if (parser.getName() != null && parser.getEventType() == XmlPullParser.START_TAG
						&& parser.getName().equals(key)) {
					parser.next();
					// test if content matches the regexp
					if (regexp == null || parser.getText().matches(regexp)) {
						current = parser.getText();
						status = true;
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			Log.d(DavDroidConstants.LOG_TAG, "Unable to get next element", e);
			return false;
		}
	}

	/**
	 * Returns the last object read
	 */
	public String next() {
		if (!status) {
			if (!hasNext()) {
				return null;
			}
		}
		return current;
	}

	/**
	 * Implementation of the remove method
	 */
	public void remove() {
		// Do nothing
	}

	/**
	 * Closes the XML stream
	 */
	public void close() {
		try {
			// finishes reading stream
			while (parser.next() != XmlPullParser.END_DOCUMENT)
				;
		} catch (XmlPullParserException e) {
			// do nothing
		} catch (IOException e) {
			// do nothing
		}
	}

}
