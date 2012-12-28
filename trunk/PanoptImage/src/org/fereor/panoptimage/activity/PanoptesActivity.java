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

package org.fereor.panoptimage.activity;

import java.sql.SQLException;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.db.DatabaseHelper;
import org.fereor.panoptimage.dao.db.DatabaseStatus;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.util.PanoptesConstants;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public abstract class PanoptesActivity extends FragmentActivity {
	/** Database helper to access DB through ORMLite */
	protected DatabaseHelper databaseHelper = null;
	/** date of Config table read */
	protected long configRead = 0;
	/** date of WebdavParam table read */
	protected long webdavRead = 0;
	/** date of LocalParam table read */
	protected long localRead = 0;
	/** font for tooltips */
	protected Typeface tooltipfont;
	/** tooltip visibility */
	protected boolean tooltipvisible = false;

	// -------------------------------------------------------------------------
	// Methods for DB access
	// -------------------------------------------------------------------------
	/**
	 * get the current helper
	 * 
	 * @return
	 */
	protected DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tooltipfont = Typeface.createFromAsset(getAssets(), "hansa.ttf");
	}

	@Override
	protected void onResume() {
		Config data;
		try {
			data = getHelper().getConfigDao().queryForId(Config.DEFAULT_KEY);
			tooltipvisible = data.isShowtip();
			displayTooltips();
		} catch (SQLException e) {
			// config not available, hide tooltips
			tooltipvisible = false;
			displayTooltips();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Release the helper when done.
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	/**
	 * check network availability
	 * 
	 * @param status
	 */
	protected boolean isNetworkAvailable() {
		// test network availability
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnected();
	}

	/**
	 * Show tooltips
	 */
	protected abstract void displayTooltips();

	/**
	 * Show tooltips displayed
	 */
	protected void showTooltip(int tid) {
		TextView txt;
		// Identify texts to display
		txt = (TextView) findViewById(tid);
		txt.setTypeface(tooltipfont);
		txt.setVisibility(tooltipvisible ? View.VISIBLE : View.INVISIBLE);
	}

	// -------------------------------------------------------------------------
	// Methods for Database status
	// -------------------------------------------------------------------------
	public void markConfigRead() {
		configRead = System.currentTimeMillis();
	}

	protected boolean isConfigUptodate() {
		return configRead > DatabaseStatus.getInstance().getConfigUpdated();
	}

	public void markWebdavRead() {
		webdavRead = System.currentTimeMillis();
	}

	protected boolean isWebdavParamUptodate() {
		return webdavRead > DatabaseStatus.getInstance().getWebdavUpdated();
	}

	public void markLocalRead() {
		localRead = System.currentTimeMillis();
	}

	protected boolean isLocalParamUptodate() {
		return localRead > DatabaseStatus.getInstance().getLocalUpdated();
	}

	/**
	 * Get tooltip font
	 * 
	 * @return
	 */
	public Typeface getTooltipFont() {
		return tooltipfont;
	}

	/**
	 * Get tooltip visibility
	 * 
	 * @return
	 */
	public boolean isTooltipVisible() {
		return tooltipvisible;
	}

	// -------------------------------------------------------------------------
	// Methods for Messages
	// -------------------------------------------------------------------------
	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public void showErrorMsg(int stringRes, Object... args) {
		String msg = String.format(getString(stringRes), args);
		showErrorMsg(msg);
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public void showErrorMsg(Exception e) {
		showErrorMsg(e.toString());
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public void showInfoMsg(int stringRes, Object... args) {
		String msg = getString(stringRes, args);
		showInfoMsg(msg);
	}

	/**
	 * Display a message
	 * 
	 * @param msg message to display
	 */
	public void showInfoMsg(String msg) {
		Log.d(PanoptesConstants.TAGNAME, "Info:" + msg);
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public void showErrorMsg(String msg) {
		Log.d(PanoptesConstants.TAGNAME, "Error:" + msg);
		Toast.makeText(this, getString(R.string.error_header, msg), Toast.LENGTH_LONG).show();
	}

}
