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

@DatabaseTable(tableName = LocalParam.LOCALPARAM_TABLE_NAME)
public class LocalParam extends CreateParam {
	public static final String LOCALPARAM_TABLE_NAME = "localparam";
	public static final String LOCALPARAM_KEY = "key";
	public static final String LOCALPARAM_PATH = "path";
	@DatabaseField(id = true, columnName = LOCALPARAM_KEY)
	private String key;
	@DatabaseField(columnName = LOCALPARAM_PATH)
	private String path;

	public LocalParam() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(key);
		dest.writeString(path);
	}

	/**
	 * Creator of the parcelable
	 */
	public static final Parcelable.Creator<LocalParam> CREATOR = new Parcelable.Creator<LocalParam>() {
		public LocalParam createFromParcel(Parcel in) {
			return new LocalParam(in);
		}

		public LocalParam[] newArray(int size) {
			return new LocalParam[size];
		}
	};

	/**
	 * private constructor for internal use only
	 * 
	 * @param in parcel to read from
	 */
	private LocalParam(Parcel in) {
		key = in.readString();
		path = in.readString();
	}

}
