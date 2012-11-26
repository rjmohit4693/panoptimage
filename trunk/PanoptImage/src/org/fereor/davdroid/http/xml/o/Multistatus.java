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
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Class to import from Multistatus response (PROPFIND method)
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class Multistatus implements DavOutput {
	/** name of tag */
	public static final String TAGNAME = "multistatus";
	
	/** List of responses */
	protected List<Response> response;

	/** Description of response */
	protected String responsedescription;

	/**
	 * Gets the value of the response property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the response property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getResponse().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Response }
	 * 
	 * 
	 */
	public List<Response> getResponse() {
		if (response == null) {
			response = new ArrayList<Response>();
		}
		return this.response;
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
