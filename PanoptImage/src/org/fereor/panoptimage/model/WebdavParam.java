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

@DatabaseTable(tableName = WebdavParam.WEBDAVPARAM_TABLE_NAME)
public class WebdavParam extends CreateParam {
	/** serial UID */
	private static final long serialVersionUID = -5094232537424280861L;
	public static final String WEBDAVPARAM_TABLE_NAME = "webdavparam";
	public static final String WEBDAVPARAM_KEY = "key";
	public static final String WEBDAVPARAM_PROTOCOL = "protocol";
	public static final String WEBDAVPARAM_SERVER = "server";
	public static final String WEBDAVPARAM_PORT = "port";
	public static final String WEBDAVPARAM_USER = "user";
	public static final String WEBDAVPARAM_PWD = "pwd";
	public static final String WEBDAVPARAM_BASE = "base";
	public static final String WEBDAVPARAM_PATH = "path";

	@DatabaseField(id = true, columnName = WEBDAVPARAM_KEY)
	private String key;
	@DatabaseField(columnName = WEBDAVPARAM_PROTOCOL)
	private String protocol;
	@DatabaseField(columnName = WEBDAVPARAM_SERVER)
	private String server;
	@DatabaseField(columnName = WEBDAVPARAM_PORT)
	private String port;
	@DatabaseField(columnName = WEBDAVPARAM_USER)
	private String user;
	@DatabaseField(columnName = WEBDAVPARAM_PWD)
	private String pwd;
	@DatabaseField(columnName = WEBDAVPARAM_BASE)
	private String base;
	@DatabaseField(columnName = WEBDAVPARAM_PATH)
	private String path;

	public WebdavParam() {
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean hasData() {
		return true;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
