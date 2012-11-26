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

import java.util.Collection;

/**
 * Resourcetype field
 * 
 * @author "arnaud.p.fereor"
 */
public class Resourcetype {

	private Collection collection;

	/**
	 * Gets the value of the collection property.
	 * 
	 * @return possible object is {@link Collection }
	 * 
	 */
	public Collection getCollection() {
		return collection;
	}

	/**
	 * Sets the value of the collection property.
	 * 
	 * @param value
	 *            allowed object is {@link Collection }
	 * 
	 */
	public void setCollection(Collection value) {
		this.collection = value;
	}

}
