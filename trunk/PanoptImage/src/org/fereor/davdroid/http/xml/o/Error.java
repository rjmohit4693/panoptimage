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

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Error implements DavOutput {
	/** name of tag */
	public static final String TAGNAME = "error";
	
	protected Object any;

	/**
	 * Gets the value of the any property.
	 * 
	 * @return possible object is {@link Object }
	 * 
	 */
	public Object getAny() {
		return any;
	}

	/**
	 * Sets the value of the any property.
	 * 
	 * @param value
	 *            allowed object is {@link Object }
	 * 
	 */
	public void setAny(Object value) {
		this.any = value;
	}

	@Override
	public void readself(XmlPullParser parser)
			throws XmlPullParserException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recycle(boolean status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRecycled() {
		// TODO Auto-generated method stub
		return false;
	}

}
