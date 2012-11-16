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

package org.fereor.panoptimage.activity.config;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.DatabaseHelper;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.util.PanoptesConstants;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class ConfigActivity extends Activity {
	/** Database helper to access DB through ORMLite */
	private DatabaseHelper databaseHelper = null;
	/** Config information */
	private Config data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(PanoptesConstants.TAGNAME, "ConfigActivity:onCreate");
		// set Content view
		setContentView(R.layout.activity_config);
		// update config data
		try {
			loadConfig();
		} catch (SQLException e) {
			Log.e(PanoptesConstants.TAGNAME, "Cannot load config : " + e.toString());
		}
		// put data in fields
		fillFields();
	}

	/**
	 * Called when the 'back' button is clicked
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		Log.d(PanoptesConstants.TAGNAME, "ConfigActivity:doBack");
		try {
			readFields();
			saveConfig(data);
		} catch (Exception e) {
			Log.e(PanoptesConstants.TAGNAME, "Cannot update config : " + e.toString());
		}
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(PanoptesConstants.TAGNAME, "ConfigActivity:onDestroy");
		// Release the helper when done.
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	/**
	 * get the current helper
	 * 
	 * @return
	 */
	private DatabaseHelper getHelper() {
		if (databaseHelper == null) {
			databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
		}
		return databaseHelper;
	}

	/**
	 * read content of config into screen fields
	 */
	private void readFields() {
		EditText proxyIp = (EditText) findViewById(R.id.param_proxy_ip_value);
		data.setProxyIp(proxyIp.getText().toString());
		EditText proxyPort = (EditText) findViewById(R.id.param_proxy_port_value);
		data.setProxyPort(Integer.parseInt(proxyPort.getText().toString()));
		CheckBox showtip = (CheckBox) findViewById(R.id.param_showtip_value);
		data.setShowtip(showtip.isChecked());
	}

	/**
	 * fill content of config into screen fields
	 */
	private void fillFields() {
		EditText proxyIp = (EditText) findViewById(R.id.param_proxy_ip_value);
		proxyIp.setText(data.getProxyIp());
		EditText proxyPort = (EditText) findViewById(R.id.param_proxy_port_value);
		proxyPort.setText(Integer.toString(data.getProxyPort()));
		CheckBox showtip = (CheckBox) findViewById(R.id.param_showtip_value);
		showtip.setChecked(data.isShowtip());
	}

	/**
	 * Load the config data
	 * 
	 * @throws SQLException
	 */
	private void loadConfig() throws SQLException {
		data = getHelper().getConfigDao().queryForId("default");
		if (data == null) {
			data = new Config();
		}

	}

	/**
	 * Save the config data
	 * 
	 * @throws Exception
	 */
	private void saveConfig(final Config mydata) throws Exception {
		getHelper().getConfigDao().callBatchTasks(new Callable<Void>() {
			public Void call() throws SQLException {
				// insert a number of accounts at once
				getHelper().getConfigDao().createOrUpdate(mydata);
				return null;
			}
		});
	}

}
