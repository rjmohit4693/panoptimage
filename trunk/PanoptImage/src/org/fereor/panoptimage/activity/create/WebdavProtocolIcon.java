// This file is part of panoptimage.
//
// panoptimage is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// panoptimage is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with panoptimage.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.panoptimage.activity.create;

import org.fereor.panoptimage.R;

public enum WebdavProtocolIcon {
	HTTP(
			R.drawable.ic_protocol_http),
	HTTPS(
			R.drawable.ic_protocol_https);

	// list of Webdav ports
	public static int[] WEBDAV_PORTS = { 80, 8080, 8081, 8082, 8083, 8084, 8085, 8086, 8087, 8088, 8089, 8090, 8888,
			443, 1443, 2443, 3443, 4443, 5443, 6443, 7443, 8443, 9443 };
	public static int STANDARD_PORT = 80;
	public static int SECURED_PORT = 443;

	/**
	 * Resolve the icon according to a port number
	 * 
	 * @param port port
	 * @return icon object
	 */
	public static final WebdavProtocolIcon findIcon(int port) {
		// find icon to display
		WebdavProtocolIcon icon = HTTP;
		if (Integer.toString(port).contains(Integer.toString(SECURED_PORT))) {
			icon = HTTPS;
		}
		return icon;
	}

	/** icon to display */
	private int icon;

	private WebdavProtocolIcon(int icon) {
		this.icon = icon;
	}

	public int icon() {
		return icon;
	}

	public static int indexOf(String key) {
		int pos = 0;
		for (WebdavProtocolIcon value : values()) {
			if (value.toString().equalsIgnoreCase(key)) {
				return pos;
			}
			pos++;
		}
		return pos;
	}
}
