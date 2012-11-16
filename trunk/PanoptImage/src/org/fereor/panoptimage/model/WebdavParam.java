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

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = WebdavParam.WEBDAVPARAM_TABLE_NAME)
public class WebdavParam extends CreateParam {
	public static final String WEBDAVPARAM_TABLE_NAME = "webdavparam";
	public static final String WEBDAVPARAM_KEY = "key";
	public static final String WEBDAVPARAM_PROTOCOL = "protocol";
	public static final String WEBDAVPARAM_SERVER = "server";
	public static final String WEBDAVPARAM_PORT = "port";
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
	@DatabaseField(columnName = WEBDAVPARAM_BASE)
	private String base;
	@DatabaseField(columnName = WEBDAVPARAM_PATH)
	private String path;

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

	public WebdavParam() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(key);
		dest.writeString(protocol);
		dest.writeString(server);
		dest.writeString(port);
		dest.writeString(base);
		dest.writeString(path);
	}

	/**
	 * Creator of the parcelable
	 */
	public static final Parcelable.Creator<WebdavParam> CREATOR = new Parcelable.Creator<WebdavParam>() {
		public WebdavParam createFromParcel(Parcel in) {
			return new WebdavParam(in);
		}

		public WebdavParam[] newArray(int size) {
			return new WebdavParam[size];
		}
	};

	/**
	 * private constructor for internal use only
	 * 
	 * @param in parcel to read from
	 */
	private WebdavParam(Parcel in) {
		key = in.readString();
		protocol = in.readString();
		server = in.readString();
		port = in.readString();
		base = in.readString();
		path = in.readString();
	}
}
