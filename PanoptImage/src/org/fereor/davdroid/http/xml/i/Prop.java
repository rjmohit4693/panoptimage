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

package org.fereor.davdroid.http.xml.i;

import java.io.IOException;
import java.util.HashMap;

import org.fereor.davdroid.http.xml.o.DavOutput;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/**
 * Class to manipulate Prop array
 * 
 * @author "arnaud.p.fereor"
 */
public class Prop implements DavInput, DavOutput {
	/** name of tag */
	public static final String TAGNAME = "prop";

	/** Properties */
	private HashMap<String, String> properties = new HashMap<String, String>();

	/**
	 * Gets the list of properties. If none set, create the map
	 * 
	 * @return
	 */
	public HashMap<String, String> getProperties() {
		return properties;
	}

	/**
	 * Gets the list of properties. If none set, create the map
	 * 
	 * @return
	 */
	public void addProperty(String key, String value) {
		properties.put(key, value);
	}

	/**
	 * Serialize the current object using serializer
	 * 
	 * @param serializer
	 *            Xml serializer to use
	 */
	public void writeself(XmlSerializer serializer) throws IOException {
		// write root tag
		serializer.startTag(NAMESPACE, TAGNAME);
		if (properties != null) {
			for (String key : properties.keySet()) {
				// put all properties
				serializer.startTag(NAMESPACE, key);
				if (properties.get(key) != null) {
					serializer.text(properties.get(key));
				}
				serializer.endTag(NAMESPACE, key);
			}
		}
		// write end tag
		serializer.endTag(NAMESPACE, TAGNAME);
	}

	@Override
	public void readself(XmlPullParser parser) throws XmlPullParserException,
			IOException {
		// Check validity of current tag (should be prop)
		parser.require(XmlPullParser.START_TAG, NAMESPACE, TAGNAME);
		String name = null;
		String value = null;
		// read tags
		while (parser.next() != XmlPullParser.END_TAG
				|| !TAGNAME.equals(parser.getName())) {
			if (parser.getEventType() == XmlPullParser.START_TAG) {
				// read next property
				name = parser.getName();
			} else if (parser.getEventType() == XmlPullParser.TEXT) {
				// read content (if present)
				value = parser.getText();
			} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				// store property
				addProperty(name, value);
			}
		}
	}

	@Override
	public void recycle(boolean status) {
		// do nothing
	}

	@Override
	public boolean isRecycled() {
		return false;
	}
}
