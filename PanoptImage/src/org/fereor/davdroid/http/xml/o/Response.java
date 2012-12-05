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

public class Response implements DavOutput {
	public static final String TAGNAME = "response";
	private static final String HREF = "href";
	private static final String STATUS = "status";
	private static final String RESPONSEDESC = "responsedescription";

	private String href;

	private String status;

	private String responsedescription;
	
	private Propstat propstat;

	private Error error;

	private Prop prop;

	/**
	 * Default constructor
	 */
	public Response() {
		super();
	}

	@Override
	public void recycle(boolean status) {
	}

	@Override
	public boolean isRecycled() {
		return false;
	}

	/**
	 * Reads the content of the object from a XML stream
	 */
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
				readfield(name, parser);
			} else if (parser.getEventType() == XmlPullParser.TEXT) {
				// read content (if present)
				value = parser.getText();
			} else if (parser.getEventType() == XmlPullParser.END_TAG) {
				// store property
				setField(name, value);
			}
		}
	}

	/**
	 * Method to read a sub element
	 * 
	 * @param name
	 *            name of the element to read
	 * @param parser
	 *            parser to read the content of the element
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	private void readfield(String name, XmlPullParser parser)
			throws XmlPullParserException, IOException {
		if (Prop.TAGNAME.equals(name)) {
			// read a Prop object
			Prop field = new Prop();
			field.readself(parser);
			prop = field;
		} else if (Error.TAGNAME.equals(name)) {
			// read a Error object
			Error field = new Error();
			field.readself(parser);
			error = field;
		} else if (Propstat.TAGNAME.equals(name)) {
			// read a Propstat object
			Propstat field = new Propstat();
			field.readself(parser);
			propstat = field;
		} else {
			// read as string
			String value = null;
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
					setField(name, value);
				}
			}
		}
	}

	/**
	 * Sets the value of a field
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            field value
	 */
	private void setField(String name, String value) {
		if(HREF.equals(name))
		{
			href=value;
		} else if(STATUS.equals(name))
		{
			status = value;
		}else if(RESPONSEDESC.equals(name))
		{
			responsedescription = value;
		}
	}

	/**
	 * Gets the value of the href property.
	 */
	public String getHref() {
		if (href == null) {
			href = new String();
		}
		return href;
	}

	/**
	 * Gets the value of the status property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Gets the value of the propstat property.
	 * <p/>
	 * <p/>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the propstat property.
	 * <p/>
	 * <p/>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getPropstat().add(newItem);
	 * </pre>
	 * <p/>
	 * <p/>
	 * <p/>
	 * Objects of the following type(s) are allowed in the list {@link Propstat }
	 */
	public Propstat getPropstat() {
		if (propstat == null) {
			propstat = new Propstat();
		}
		return this.propstat;
	}

	/**
	 * Gets the value of the error property.
	 * 
	 * @return possible object is {@link Error }
	 */
	public Error getError() {
		return error;
	}

	/**
	 * Gets the value of the responsedescription property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getResponsedescription() {
		return responsedescription;
	}

	/**
	 * getter for Prop field
	 * 
	 * @return prop
	 */
	public Prop getProp() {
		return prop;
	}

}
