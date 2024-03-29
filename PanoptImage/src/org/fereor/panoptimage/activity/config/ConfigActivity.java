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
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.dao.db.DatabaseHelper;
import org.fereor.panoptimage.dao.db.DatabaseStatus;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.util.PanoptimageConstants;
import org.fereor.panoptimage.util.PanoptimageMsg;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class ConfigActivity extends PanoptesActivity {
	/** Database helper to access DB through ORMLite */
	private DatabaseHelper databaseHelper = null;
	/** Config information */
	private Config data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(PanoptimageConstants.TAGNAME, "ConfigActivity:onCreate");
		// set Content view
		setContentView(R.layout.activity_config);

		try {
			// update config data
			loadConfig();
			fillFields();
		} catch (SQLException e) {
			Log.e(PanoptimageConstants.TAGNAME, "Cannot load config : " + e.toString());
			PanoptimageMsg.showErrorMsg(this, R.string.error_load_cfg, e.toString());
		}

	}

	/**
	 * Called when the 'back' button is clicked
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		Log.d(PanoptimageConstants.TAGNAME, "ConfigActivity:doBack");
		finish();
	}

	/**
	 * Called when the 'save' button is clicked
	 * 
	 * @param view
	 */
	public void doSave(View view) {
		Log.d(PanoptimageConstants.TAGNAME, "ConfigActivity:doSave");
		try {
			// save data from fields
			readFields();
			saveConfig(data);
		} catch (Exception e) {
			PanoptimageMsg.showErrorMsg(this, R.string.error_save_cfg, e.toString());
			return;
		}
		PanoptimageMsg.showInfoMsg(this, R.string.param_message_saved);
		DatabaseStatus.getInstance().markConfigSaved();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(PanoptimageConstants.TAGNAME, "ConfigActivity:onDestroy");
		// Release the helper when done.
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			databaseHelper = null;
		}
	}

	/**
	 * read content of config into screen fields
	 */
	private void readFields() {
		// EditText proxyIp = (EditText) findViewById(R.id.param_proxy_ip_value);
		// data.setProxyIp(proxyIp.getText().toString());
		// EditText proxyPort = (EditText) findViewById(R.id.param_proxy_port_value);
		// data.setProxyPort(Integer.parseInt(proxyPort.getText().toString()));
		CheckBox showtip = (CheckBox) findViewById(R.id.param_showtip_value);
		data.setShowtip(showtip.isChecked());
		CheckBox showtuto = (CheckBox) findViewById(R.id.param_showtuto_value);
		data.setShowtuto(showtuto.isChecked());
		data.setMemoptim(0);
	}

	/**
	 * fill content of config into screen fields
	 */
	private void fillFields() {
		// proxy IP
		// EditText proxyIp = (EditText) findViewById(R.id.param_proxy_ip_value);
		// proxyIp.setText(data.getProxyIp());
		// proxy port
		// EditText proxyPort = (EditText) findViewById(R.id.param_proxy_port_value);
		// proxyPort.setText(Integer.toString(data.getProxyPort()));
		// Show tip
		CheckBox showtip = (CheckBox) findViewById(R.id.param_showtip_value);
		showtip.setChecked(data.isShowtip());
		CheckBox showtuto = (CheckBox) findViewById(R.id.param_showtuto_value);
		showtuto.setChecked(data.isShowtuto());
	}

	/**
	 * Load the config data
	 * 
	 * @throws SQLException
	 */
	private void loadConfig() throws SQLException {
		data = getHelper().getConfigDao().queryForId(Config.DEFAULT_KEY);
		if (data == null) {
			data = new Config();
			data.setKey(Config.DEFAULT_KEY);
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

	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_save);
		showTooltip(R.id.tooltip_back);
	}

	@Override
	protected void displayTutorials() {
	}

}
