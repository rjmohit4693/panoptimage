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

import java.sql.SQLException;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.util.PanoptimageConstants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Database helper used to manage all table configuration at application load
 * 
 * @author "arnaud.p.fereor"
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	// name of the database file for your application -- change to something
	// appropriate for your app
	private static final String DATABASE_NAME = "panoptes.db";
	// any time you make changes to your database objects, you may have to
	// increase the database version
	private static final int DATABASE_VERSION = 3;

	// the DAO object we use to access the Player table
	private Dao<Config, String> configDao = null;

	// the DAO object we use to access the WebdavParam table
	private Dao<WebdavParam, String> webdavParamDao = null;

	// the DAO object we use to access the LocalParam table
	private Dao<LocalParam, String> localParamDao = null;
	
	/**
	 * Constructor : initialize everything
	 * 
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource source) {
		Log.d(PanoptimageConstants.TAGNAME, "DatabaseHelper:onCreate");
		try {
			// create tables in database
			for (Class<?> nextClass : PanoptimageConstants.DATABASE_CLASSES) {
				TableUtils.createTable(connectionSource, nextClass);
			}
		} catch (SQLException e) {
			Log.e(PanoptimageConstants.TAGNAME, "Can't create database", e);
			throw new RuntimeException(e);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource source, int oldVersion, int newVersion) {
		Log.d(PanoptimageConstants.TAGNAME, "DatabaseHelper:onUpgrade");
		try {
			// Drop all tables
			for (Class<?> nextClass : PanoptimageConstants.DATABASE_CLASSES) {
				TableUtils.dropTable(connectionSource, nextClass, true);
			}
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(PanoptimageConstants.TAGNAME, "Can't drop databases", e);
		}
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		Log.d(PanoptimageConstants.TAGNAME, "DatabaseHelper:onOpen");
		super.onOpen(db);
		// Initialize DAOs
		try {
			getWebdavParamDao();
			getLocalParamDao();
			getConfigDao();
		} catch (SQLException e) {
			Log.e(PanoptimageConstants.TAGNAME, "Can't open databases", e);
		}
	}

	/**
	 * Create the DAO for WebdavParam
	 * 
	 * @return dao for WebdavParam
	 * @throws SQLException
	 */
	public Dao<WebdavParam, String> getWebdavParamDao() throws SQLException {
		if (webdavParamDao == null) {
			webdavParamDao = getDao(WebdavParam.class);
		}
		return webdavParamDao;
	}

	/**
	 * Create the DAO for LocalParam
	 * 
	 * @return dao for LocalParam
	 * @throws SQLException
	 */
	public Dao<LocalParam, String> getLocalParamDao() throws SQLException {
		if (localParamDao == null) {
			localParamDao = getDao(LocalParam.class);
		}
		return localParamDao;
	}

	/**
	 * Create the DAO for Config
	 * 
	 * @return dao for Config
	 * @throws SQLException
	 */
	public Dao<Config, String> getConfigDao() throws SQLException {
		if (configDao == null) {
			configDao = getDao(Config.class);
		}
		return configDao;
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		configDao = null;
		webdavParamDao = null;
		localParamDao = null;
	}
}
