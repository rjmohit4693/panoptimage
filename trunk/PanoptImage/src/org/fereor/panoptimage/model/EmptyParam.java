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

@DatabaseTable(tableName = EmptyParam.EMPTYPARAM_TABLE_NAME)
public class EmptyParam extends CreateParam {
	/** serial UID */
	private static final long serialVersionUID = -2356459204231787680L;
	public static final String EMPTYPARAM_TABLE_NAME = "";
	public static final String EMPTYPARAM_KEY = "key";

	@DatabaseField(id = true, columnName = EMPTYPARAM_KEY)
	private String key;

	public EmptyParam() {
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public void setPath(String path) {
		return;
	}

	@Override
	public boolean hasData() {
		return false;
	}

	@Override
	public boolean needNetwork() {
		return false;
	}
}
