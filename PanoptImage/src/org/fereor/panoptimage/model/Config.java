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

package org.fereor.panoptimage.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Config.CONFIG_TABLE_NAME)
public class Config {

	public static final String CONFIG_TABLE_NAME = "config";
	public static final String CONFIG_KEY = "key";
	public static final String CONFIG_PROXY_IP = "proxy_ip";
	public static final String CONFIG_PROXY_PORT = "proxy_port";
	public static final String CONFIG_SHOWTIP = "showtip";
	@DatabaseField(id = true, columnName = CONFIG_KEY)
	private String key;
	@DatabaseField(columnName = CONFIG_PROXY_IP)
	private String proxyIp;
	@DatabaseField(columnName = CONFIG_PROXY_PORT)
	private int proxyPort;
	@DatabaseField(columnName = CONFIG_SHOWTIP)
	private boolean showtip;

	/**
	 * Empty constructor (mandatory for ORMLite)
	 */
	public Config() {
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public boolean isShowtip() {
		return showtip;
	}

	public void setShowtip(boolean showtip) {
		this.showtip = showtip;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
