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

package org.fereor.davdroid.http.request;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.fereor.davdroid.http.enums.HttpDepth;
import org.fereor.davdroid.http.enums.HttpMethod;
/**
 * Method for a PROPFIND request
 * @author "arnaud.p.fereor"
 */
public class HttpPropfind extends HttpEntityEnclosingRequestBase {

	/**
	 * Default constructor
	 * 
	 * @param url
	 *            The resource
	 */
	public HttpPropfind(final String url, String properties, HttpDepth depth) {
		this(URI.create(url), properties, depth);
	}

	/**
	 * Default constructor
	 * 
	 * @param url
	 *            The resource
	 */
	public HttpPropfind(final URI url, String properties, HttpDepth depth) {
		this.setURI(url);
		this.setDepth(depth);
		this.setHeader("Content-Type", "application/xml" + HTTP.CHARSET_PARAM
				+ HTTP.UTF_8.toLowerCase());
		try {
			StringEntity myEntity = new StringEntity(properties,
					HTTP.UTF_8.toLowerCase());
			this.setEntity(myEntity);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * A client may set the depth of the request
	 * 
	 * @param depth
	 *            <code>0</code> or <code>1</code> or <code>infinity</code>
	 */
	public void setDepth(HttpDepth depth) {
		this.setHeader("Depth", depth.toString());
	}

	@Override
	public String getMethod() {
		return HttpMethod.PROPFIND.name();
	}

}
