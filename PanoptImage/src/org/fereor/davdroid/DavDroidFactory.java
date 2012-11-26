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

package org.fereor.davdroid;

import java.net.ProxySelector;

import org.apache.http.HttpHost;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Factory to create the DavDroid implementation
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class DavDroidFactory {
	/**
	 * Default init() with no authentication and default settings for SSL.
	 * @throws XmlPullParserException 
	 */
	public static DavDroid init(HttpHost host) throws XmlPullParserException {
		return init(host, null, null);
	}

	/**
	 * Pass in a HTTP Auth username/password for being used with all connections
	 * 
	 * @param username
	 *            Use in authentication header credentials
	 * @param password
	 *            Use in authentication header credentials
	 * @throws XmlPullParserException 
	 */
	public static DavDroid init(HttpHost host, String username, String password) throws XmlPullParserException {
		return init(host, username, password, null);
	}

	/**
	 * @param username
	 *            Use in authentication header credentials
	 * @param password
	 *            Use in authentication header credentials
	 * @param proxy
	 *            Proxy configuration
	 * @throws XmlPullParserException 
	 */
	public static DavDroid init(HttpHost host, String username,
			String password, ProxySelector proxy) throws XmlPullParserException {
		return new DavDroid(host, username, password, proxy);
	}
}