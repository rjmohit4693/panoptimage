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

import org.fereor.davdroid.http.xml.i.Prop;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class Propstat implements DavOutput {
	/** Name of tag */
	public static final String TAGNAME = "propstat";
	
	protected Prop prop;

	protected String status;

	protected Error error;

	protected String responsedescription;

	/**
	 * Gets the value of the prop property.
	 * 
	 * @return possible object is {@link Prop }
	 * 
	 */
	public Prop getProp() {
		return prop;
	}

	/**
	 * Sets the value of the prop property.
	 * 
	 * @param value
	 *            allowed object is {@link Prop }
	 * 
	 */
	public void setProp(Prop value) {
		this.prop = value;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the value of the status property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setStatus(String value) {
		this.status = value;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * @return possible object is {@link Error }
	 * 
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Sets the value of the error property.
	 * 
	 * @param value
	 *            allowed object is {@link Error }
	 * 
	 */
	public void setError(Error value) {
		this.error = value;
	}

	/**
	 * Gets the value of the responsedescription property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getResponsedescription() {
		return responsedescription;
	}

	/**
	 * Sets the value of the responsedescription property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setResponsedescription(String value) {
		this.responsedescription = value;
	}

	@Override
	public void readself(XmlPullParser parser) throws XmlPullParserException,
			IOException {
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
