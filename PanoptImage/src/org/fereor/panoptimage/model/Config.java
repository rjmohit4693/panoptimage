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
	public static final String DEFAULT_KEY = "default";
	public static final String CONFIG_TABLE_NAME = "config";
	public static final String CONFIG_KEY = "key";
	// public static final String CONFIG_PROXY_IP = "proxy_ip";
	// public static final String CONFIG_PROXY_PORT = "proxy_port";
	public static final String CONFIG_SHOWTIP = "showtip";
	public static final String CONFIG_SHOWTUTO = "showtuto";
	public static final String CONFIG_MEMOPTIM = "memoptim";
	public static final String CONFIG_LASTVIEW = "lastview";

	@DatabaseField(id = true, columnName = CONFIG_KEY)
	private String key;
	// @DatabaseField(columnName = CONFIG_PROXY_IP)
	// private String proxyIp;
	// @DatabaseField(columnName = CONFIG_PROXY_PORT)
	// private int proxyPort;
	@DatabaseField(columnName = CONFIG_SHOWTIP)
	private boolean showtip;
	@DatabaseField(columnName = CONFIG_SHOWTUTO)
	private boolean showtuto;
	@DatabaseField(columnName = CONFIG_MEMOPTIM)
	private int memoptim;
	@DatabaseField(columnName = CONFIG_LASTVIEW)
	private String lastview;

	/**
	 * Empty constructor (mandatory for ORMLite)
	 */
	public Config() {
	}

	// public String getProxyIp() {
	// return proxyIp;
	// }
	//
	// public void setProxyIp(String proxyIp) {
	// this.proxyIp = proxyIp;
	// }
	//
	// public int getProxyPort() {
	// return proxyPort;
	// }
	//
	// public void setProxyPort(int proxyPort) {
	// this.proxyPort = proxyPort;
	// }

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

	/**
	 * @return the memoptim
	 */
	public int getMemoptim() {
		return memoptim;
	}

	/**
	 * @param memoptim the memoptim to set
	 */
	public void setMemoptim(int memoptim) {
		this.memoptim = memoptim;
	}

	/**
	 * @return the showtuto
	 */
	public boolean isShowtuto() {
		return showtuto;
	}

	/**
	 * @param showtuto the showtuto to set
	 */
	public void setShowtuto(boolean showtuto) {
		this.showtuto = showtuto;
	}

	/**
	 * @return the lastview
	 */
	public String getLastview() {
		return lastview;
	}

	/**
	 * @param lastview the lastview to set
	 */
	public void setLastview(String lastview) {
		this.lastview = lastview;
	}

}
