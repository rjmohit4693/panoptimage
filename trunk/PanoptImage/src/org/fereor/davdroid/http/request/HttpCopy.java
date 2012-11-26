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

import java.net.URI;

import org.apache.http.client.methods.HttpRequestBase;
import org.fereor.davdroid.http.enums.HttpMethod;

/**
 * Simple class for making WebDAV <code>COPY</code> requests. Assumes Overwrite = T.
 */
/**
 * Simple class to <code>COPY</code> request.
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class HttpCopy extends HttpRequestBase {
	public static final String METHOD_NAME = "COPY";

	/**
	 * Creates a COPY method
	 * 
	 * @param source
	 *            source URL to copy
	 * @param destination
	 *            destination to copy
	 */
	public HttpCopy(URI source, URI destination, boolean overwrite) {
		this.setHeader("Destination", destination.toString());
		this.setHeader("Overwrite", overwrite ? "T" : "F");
		this.setURI(source);
	}

	/**
	 * Creates a COPY method
	 * 
	 * @param source
	 *            source source URL to copy
	 * @param destination
	 *            destination to copy
	 */
	public HttpCopy(String source, String destination, boolean overwrite) {
		this(URI.create(source), URI.create(destination), overwrite);
	}

	@Override
	public String getMethod() {
		return HttpMethod.COPY.name();
	}
}
