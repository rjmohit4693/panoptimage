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

@DatabaseTable(tableName = LocalParam.LOCALPARAM_TABLE_NAME)
public class LocalParam extends CreateParam {
	/** serial UID */
	private static final long serialVersionUID = -7763624090962859328L;
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

	@Override
	public boolean hasData(){
		return true;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public boolean needNetwork() {
		return false;
	}
}
