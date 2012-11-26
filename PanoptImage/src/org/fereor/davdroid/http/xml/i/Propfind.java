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

import org.xmlpull.v1.XmlSerializer;

/**
 * Basic class for Propfind message
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class Propfind implements DavInput {
	/** Allprop field */
	private Allprop allprop;
	/** Propname field */
	private Propname propname;
	/** Prop field */
	private Prop prop;

	/**
	 * Create a simple propfind object with allprop set
	 * 
	 * @return Profind for Allprop request
	 */
	public static Propfind createAllprop() {
		Propfind props = new Propfind();
		props.setAllprop(new Allprop());
		return props;
	}

	/**
	 * Create a simple propfind object with propname set
	 * 
	 * @return Profind for Propname request
	 */
	public static Propfind createPropname() {
		Propfind props = new Propfind();
		props.setPropname(new Propname());
		return props;
	}

	/**
	 * Create a simple propfind object with props set
	 * 
	 * @param creationdate
	 * @param getlastmodified
	 * @param getetag
	 * @param getcontenttype
	 * @return
	 */
	public static Propfind createProp(String[] keys) {
		Propfind props = new Propfind();
		Prop prop = new Prop();
		// put all properties specified
		for (String key : keys) {
			prop.addProperty(key, null);
		}
		props.setProp(prop);
		return props;
	}

	/**
	 * Serialize the current object using serializer
	 * 
	 * @param serializer
	 *            Xml serializer to use
	 * @throws IOException
	 */
	public void writeself(XmlSerializer serializer) throws IOException {
		serializer.startTag(NAMESPACE, name());
		if (allprop != null) {
			allprop.writeself(serializer);
		} else if (propname != null) {
			propname.writeself(serializer);
		} else if (prop != null) {
			prop.writeself(serializer);
		}
		serializer.endTag(NAMESPACE, name());
	}

	/**
	 * Returns the name of this object
	 */
	public String name() {
		return getClass().getSimpleName().toLowerCase();
	}

	/**
	 * Gets the value of the allprop property.
	 * 
	 * @return possible object is {@link Allprop }
	 * 
	 */
	public Allprop getAllprop() {
		return allprop;
	}

	/**
	 * Sets the value of the allprop property.
	 * 
	 * @param value
	 *            allowed object is {@link Allprop }
	 * 
	 */
	public void setAllprop(Allprop value) {
		this.allprop = value;
	}

	/**
	 * Gets the value of the propname property.
	 * 
	 * @return possible object is {@link Propname }
	 * 
	 */
	public Propname getPropname() {
		return propname;
	}

	/**
	 * Sets the value of the propname property.
	 * 
	 * @param value
	 *            allowed object is {@link Propname }
	 * 
	 */
	public void setPropname(Propname value) {
		this.propname = value;
	}

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

}
