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

import org.fereor.panoptimage.dao.db.DatabaseHelper;
import org.fereor.panoptimage.dao.db.DatabaseStatus;
import org.fereor.panoptimage.model.Config;

import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
	/** tutorial visibility */
	private boolean tutorialvisible;

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
			if (data != null) {
				tooltipvisible = data.isShowtip();
				tutorialvisible = data.isShowtuto();
			} else {
				tooltipvisible = false;
				tutorialvisible = true;
			}
			displayTooltips();
			displayTutorials();
		} catch (SQLException e) {
			// config not available, hide tooltips
			tooltipvisible = false;
			tutorialvisible = true;
			displayTooltips();
			displayTutorials();
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
	 * Show tooltips
	 */
	protected abstract void displayTutorials();

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

	/**
	 * Hide tooltips displayed
	 */
	protected void hideTooltip(int tid) {
		TextView txt;
		// Identify texts to display
		txt = (TextView) findViewById(tid);
		txt.setVisibility(View.INVISIBLE);
	}

	/**
	 * Show tooltips displayed
	 */
	protected void showTutorial(int tid) {
		TextView txt;
		// Identify texts to display
		txt = (TextView) findViewById(tid);
		txt.setTypeface(tooltipfont);
		txt.setVisibility(tutorialvisible ? View.VISIBLE : View.INVISIBLE);
	}

	/**
	 * Hide tooltips displayed
	 */
	protected void hideTutorial(int tid) {
		TextView txt;
		// Identify texts to display
		txt = (TextView) findViewById(tid);
		txt.setVisibility(View.INVISIBLE);
	}

	/**
	 * Unbind all images
	 * 
	 * @param view
	 */
	protected void unbindDrawables(View view) {
		if (view == null)
			return;
		if (view.getBackground() != null) {
			view.getBackground().setCallback(null);
		}
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				unbindDrawables(((ViewGroup) view).getChildAt(i));
			}
			((ViewGroup) view).removeAllViews();
		}
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
}
