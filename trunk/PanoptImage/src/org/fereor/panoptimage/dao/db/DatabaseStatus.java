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

package org.fereor.panoptimage.dao.db;

public class DatabaseStatus {
	/** singleton isntance */
	private static DatabaseStatus instance = null;

	/** Config status updated */
	private long configUpdated = 0;
	/** Local status updated */
	private long localUpdated = 0;
	/** Webdav status updated */
	private long webdavUpdated = 0;

	/** get instance */
	public static DatabaseStatus getInstance() {
		if (instance == null) {
			instance = new DatabaseStatus();
		}
		return instance;
	}

	public void markConfigSaved() {
		configUpdated = System.currentTimeMillis();
	}

	public void markLocalSaved() {
		localUpdated = System.currentTimeMillis();
	}

	public void markWebdavSaved() {
		webdavUpdated = System.currentTimeMillis();
	}

	public long getConfigUpdated() {
		return configUpdated;
	}

	public long getLocalUpdated() {
		return localUpdated;
	}

	public long getWebdavUpdated() {
		return webdavUpdated;
	}

}
