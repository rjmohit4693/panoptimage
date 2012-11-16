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

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.DatabaseHelper;
import org.fereor.panoptimage.util.PanoptesConstants;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public abstract class PanoptesActivity extends FragmentActivity {
	/** Database helper to access DB through ORMLite */
	protected DatabaseHelper databaseHelper = null;

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
	protected void onDestroy() {
		super.onDestroy();
		// Release the helper when done.
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	// -------------------------------------------------------------------------
	// Methods for Error messages
	// -------------------------------------------------------------------------
	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	protected void showErrorMsg(Exception e) {
		showErrorMsg(getString(R.string.error_title), e.getMessage());
	}

	/**
	 * Display an error message
	 * 
	 * @param msg
	 */
	protected void showErrorMsg(String title, String msg) {
		Log.d(PanoptesConstants.TAGNAME, title + ":" + msg);
		Toast.makeText(this, title + ":" + msg, Toast.LENGTH_LONG).show();
	}
}
