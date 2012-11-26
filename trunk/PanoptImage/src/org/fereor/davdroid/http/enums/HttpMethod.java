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

package org.fereor.davdroid.http.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * List of WebDAV methods known
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public enum HttpMethod {
	/** List of HTTP Options */
	OPTIONS ("OPTIONS"), 
	GET ("GET"), 
	HEAD ("HEAD"), 
	POST ("POST"), 
	PUT ("PUT"),
	DELETE ("DELETE"), 
	TRACE ("TRACE"), 
	PROPFIND ("PROPFIND"), 
	PROPPATCH ("PROPPATCH"), 
	MKCOL ("MKCOL"),
	COPY ("COPY"), 
	MOVE ("MOVE"), 
	LOCK ("LOCK"), 
	UNLOCK ("UNLOCK");

	/** Value of the enum */
	private String value;
	
	/**
	 * Default constructor
	 * @param value value to set to the method value
	 */
	private HttpMethod(String value)
	{
		this.value = value;
	}
	
	/**
	 * Translate this method into a string
	 */
	public String toString()
	{
		return value;
	}
	
	/**
	 * Translate a string into List of HttpMethod
	 * @param arrays
	 * @param delim
	 * @return
	 */
	public static List<HttpMethod> parseStringArray(String arrays, String delim) {  
		// split string
		StringTokenizer strtk = new StringTokenizer(arrays, delim);
		ArrayList<HttpMethod> result = new ArrayList<HttpMethod>(strtk.countTokens());
		String tk = null;
		// identify HttpMethod values
		while(strtk.hasMoreTokens()){
			tk = strtk.nextToken();
			result.add(HttpMethod.valueOf(tk.trim()));
		}
		// return content
		return result;
	}  
}
