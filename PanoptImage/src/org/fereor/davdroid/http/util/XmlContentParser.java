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

import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.exception.DavDroidIOException;
import org.fereor.davdroid.exception.DavDroidInstanceException;
import org.fereor.davdroid.exception.DavDroidXmlException;
import org.fereor.davdroid.http.xml.o.DavOutput;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XmlContentParser<T extends DavOutput> {
	/** Class declared to create */
	private Class<T> resultClass;
	/** Xml parser */
	private XmlPullParser parser;
	private String rootnamespace;
	private String rootname;

	/**
	 * Default constructor
	 * 
	 * @param parser
	 * @param class
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public XmlContentParser(byte[] input, XmlPullParser parser,
			Class<T> resultClass, String tag) throws XmlPullParserException,
			IOException {
		this.resultClass = resultClass;
		this.parser = parser;
		this.parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
		this.parser.setInput(new ByteArrayInputStream(input), null);
		this.parser.next();
		this.parser.require(XmlPullParser.START_TAG, null, null);
		this.rootnamespace = this.parser.getNamespace();
		this.rootname = this.parser.getName();

		// advance parser to find the matching tag
		while (parser.next() != XmlPullParser.END_DOCUMENT) {
			if (tag.equals(parser.getName())) {
				break;
			}
		}

	}

	/**
	 * Parse content of data
	 * 
	 * @return new object as parsed
	 * @throws DavDroidException
	 *             if error occurred
	 */
	public T parseContent() throws DavDroidException {
		try {
			T res = resultClass.newInstance();
			res.readself(parser);
			return res;
		} catch (InstantiationException e) {
			throw new DavDroidInstanceException(resultClass.getSimpleName());
		} catch (IllegalAccessException e) {
			throw new DavDroidInstanceException(resultClass.getSimpleName());
		} catch (XmlPullParserException e) {
			throw new DavDroidXmlException(resultClass.getSimpleName());
		} catch (IOException e) {
			throw new DavDroidIOException(resultClass.getSimpleName());
		}

	}

}

