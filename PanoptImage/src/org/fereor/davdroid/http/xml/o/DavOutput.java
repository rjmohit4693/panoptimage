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

package org.fereor.davdroid.http.xml.o;

import java.io.IOException;

import org.fereor.davdroid.http.xml.DavIO;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Interface to specifiy all types parsable
 * @author "arnaud.p.fereor"
 *
 */
public interface DavOutput extends DavIO {
	/** Read itself */
	void readself(XmlPullParser parser) throws XmlPullParserException, IOException;
	/** Mark the current object as recyclable for reuse */
	void recycle(boolean status);
	/** check if object is recycled */
	boolean isRecycled();
}

