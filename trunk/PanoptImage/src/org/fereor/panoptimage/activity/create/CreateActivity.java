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

package org.fereor.panoptimage.activity.create;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.dao.async.RepositoryExistsAsync;
import org.fereor.panoptimage.dao.async.RepositoryExistsListener;
import org.fereor.panoptimage.dao.db.DatabaseStatus;
import org.fereor.panoptimage.dao.repository.WebdavRepositoryDao;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.model.CreateParam;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.fereor.panoptimage.util.PanoptesTypeEnum;
import org.fereor.panoptimage.util.PanoptimageMsg;
import org.fereor.panoptimage.util.network.HotSite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class CreateActivity extends PanoptesActivity implements OnItemSelectedListener,
		RepositoryExistsListener<Integer, Boolean> {
	private static final String SAVESTATE_CURRENTPATH = "org.fereor.panoptimage.activity.create.CreateActivity.currentpath";
	/** List of available local configurations */
	private List<? extends CreateParam> locals;
	/** List of available Webdav configurations */
	private List<? extends CreateParam> webdavs;
	/** Current parameter displayed */
	private CreateParam displayParam;
	/** Current fragment displayed */
	private CreateFragment<? extends CreateParam> displayFragment;

	// -------------------------------------------------------------------------
	// Method of Activity interface
	// -------------------------------------------------------------------------
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// set the layout
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		// Populate the spinner
		populateSpinner();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_create, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		CreateBrowserFragment browseFragment = (CreateBrowserFragment) getSupportFragmentManager().findFragmentById(
				R.id.browser_fragment);
		if (browseFragment != null) {
			outState.putString(SAVESTATE_CURRENTPATH, browseFragment.getSelectedPath());
		}
	}

	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_back);
		showTooltip(R.id.tooltip_save);
		showTooltip(R.id.tooltip_recycle);
	}

	// -------------------------------------------------------------------------
	// Methods for Action buttons
	// -------------------------------------------------------------------------
	/**
	 * Called when the Back button is clicked
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doBack");
		finish();
	}

	/**
	 * Called when the save button is clicked
	 * 
	 * @param view
	 */
	public void doSave(View view) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doSave");
		// get the param displayed
		final CreateParam param = displayFragment.readParam();
		// if param key is not set, do not save
		if (param == null || param.getKey() == null || param.getKey().trim().isEmpty())
			return;

		// call the Save method
		try {
			if (param instanceof LocalParam) {
				getHelper().getLocalParamDao().callBatchTasks(new Callable<Void>() {
					public Void call() throws SQLException {
						// insert a number of accounts at once
						getHelper().getLocalParamDao().createOrUpdate((LocalParam) param);
						DatabaseStatus.getInstance().markLocalSaved();
						return null;
					}
				});

			} else if (param instanceof WebdavParam) {
				getHelper().getWebdavParamDao().callBatchTasks(new Callable<Void>() {
					public Void call() throws SQLException {
						// insert a number of accounts at once
						getHelper().getWebdavParamDao().createOrUpdate((WebdavParam) param);
						DatabaseStatus.getInstance().markWebdavSaved();
						return null;
					}
				});

			}
		} catch (Exception e) {
			PanoptimageMsg.showErrorMsg(this, e);
		}

		// Refresh the spinner
		populateSpinner();
		// show message
		PanoptimageMsg.showInfoMsg(this, getString(R.string.create_message_saved, param.getKey()));
	}

	/**
	 * Called when the Recycle button is clicked
	 * 
	 * @param view
	 */
	public void doRecycle(View view) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doRecycle");
		final CreateParam param = displayFragment.readParam();
		// if param key is not set, do not save
		if (param == null || param.getKey() == null || param.getKey().trim().isEmpty())
			return;

		// call the delete method
		try {
			if (param instanceof LocalParam) {
				getHelper().getLocalParamDao().callBatchTasks(new Callable<Void>() {
					public Void call() throws SQLException {
						// insert a number of accounts at once
						getHelper().getLocalParamDao().delete((LocalParam) param);
						DatabaseStatus.getInstance().markLocalSaved();
						return null;
					}
				});

			} else if (param instanceof WebdavParam) {
				getHelper().getWebdavParamDao().callBatchTasks(new Callable<Void>() {
					public Void call() throws SQLException {
						// insert a number of accounts at once
						getHelper().getWebdavParamDao().delete((WebdavParam) param);
						DatabaseStatus.getInstance().markWebdavSaved();
						return null;
					}
				});

			}
		} catch (Exception e) {
			PanoptimageMsg.showErrorMsg(this, e);
		}
		// Refresh the spinner
		populateSpinner();
		// show message
		PanoptimageMsg.showInfoMsg(this, getString(R.string.create_message_deleted, param.getKey()));
	}

	/**
	 * Test server availability
	 * 
	 * @param v view clicked
	 */
	public void doTestServer(View v) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doTestServer");
		final CreateParam param = displayFragment.readParam();
		if (param == null || param.getKey() == null || param.getKey().trim().isEmpty())
			return;

		// Test network availability
		if (param.needNetwork() && !isNetworkAvailable()) {
			// Network is not available. Inform and return
			PanoptimageMsg.showErrorMsg(this, getString(R.string.error_nonetwork));
			return;
		}

		// test availability according to type of param
		if (param instanceof WebdavParam) {
			try {
				RepositoryExistsAsync task = new RepositoryExistsAsync(this, PanoptesHelper.SLASH);
				task.execute(new WebdavRepositoryDao((WebdavParam) param, getFilesDir()));
			} catch (PanoptimageNoNetworkException e) {
				PanoptimageMsg.showErrorMsg(this, getString(R.string.error_nonetwork));
				return;
			}
		}
	}

	/**
	 * Action on the Browse button
	 * 
	 * @param v view clicked
	 */
	public void doBrowse(View v) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doBrowse");

		final CreateParam param = displayFragment.readParam();

		// create the browse fragment and put it
		CreateBrowserFragment fragment = new CreateBrowserFragment();
		Bundle data = new Bundle();
		data.putSerializable(PanoptesConstants.CREATE_BUNDLE_PARAM, param);
		fragment.setArguments(data);

		getSupportFragmentManager().beginTransaction().add(R.id.browser_fragment, fragment).commit();
	}

	/**
	 * Action on validate button
	 * 
	 * @param v view clicked
	 */
	public void doBrowseValidate(View v) {
		CreateBrowserFragment browseFragment = (CreateBrowserFragment) getSupportFragmentManager().findFragmentById(
				R.id.browser_fragment);
		if (browseFragment != null) {
			String path = browseFragment.getSelectedPath();
			getSupportFragmentManager().beginTransaction().remove(browseFragment).commit();
			displayFragment.setPath(path);
		}
	}

	/**
	 * Action on cancel button
	 * 
	 * @param v view clicked
	 */
	public void doBrowseCancel(View v) {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.browser_fragment);
		if (browseFragment != null) {
			getSupportFragmentManager().beginTransaction().remove(browseFragment).commit();
		}
	}

	/**
	 * Action on the Browse button
	 * 
	 * @param v view clicked
	 */
	public void doScan(View v) {
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:doScan");
		// Check network
		if (!isNetworkAvailable()) {
			PanoptimageMsg.showInfoMsg(this, R.string.error_nonetwork);
			return;
		}
		// create the scan fragment and put it
		CreateScanFragment fragment = new CreateScanFragment();
		getSupportFragmentManager().beginTransaction().add(R.id.scan_fragment, fragment).commit();
	}

	/**
	 * Action on validate button
	 * 
	 * @param v view clicked
	 */
	public void doScanValidate(View v) {
		CreateScanFragment scanFragment = (CreateScanFragment) getSupportFragmentManager().findFragmentById(
				R.id.scan_fragment);
		if (scanFragment != null && scanFragment.isDone()) {
			HotSite site = scanFragment.getSelectedHotSite();
			getSupportFragmentManager().beginTransaction().remove(scanFragment).commit();
			// do something with site selected
			if(displayFragment instanceof WebdavCreateFragment){
				WebdavCreateFragment f = (WebdavCreateFragment)displayFragment;
				f.setServer(site.getHost().getHostAddress());
				f.setPort(site.getPort());
			}
		}
	}

	/**
	 * Action on cancel button
	 * 
	 * @param v view clicked
	 */
	public void doScanCancel(View v) {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.scan_fragment);
		if (browseFragment != null) {
			getSupportFragmentManager().beginTransaction().remove(browseFragment).commit();
		}
	}

	// -------------------------------------------------------------------------
	// Methods of interface OnItemSelectedListener
	// -------------------------------------------------------------------------
	/**
	 * Callback for item selected
	 * 
	 * @param parent current activity
	 * @param view current view
	 * @param pos position selected
	 * @param id
	 */
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		// An item was selected.
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:onItemSelected");
		try {
			displayFragment = PanoptesTypeEnum.EMPTY.instance();
			if (pos < PanoptesTypeEnum.values().length) {
				// case New... is chosen
				displayFragment = PanoptesTypeEnum.values()[pos].instance();
				displayParam = null;
			} else if ((displayParam = findLocalParamAt(pos)) != null) {
				// case local param is chosen
				displayFragment = PanoptesTypeEnum.LOCAL.instance();
			} else if ((displayParam = findWebdavParamAt(pos)) != null) {
				// case webdav param is chosen
				displayFragment = PanoptesTypeEnum.WEBDAV.instance();
			}
			displayFragment.setParam(displayParam);
			getSupportFragmentManager().beginTransaction().replace(R.id.create_fragment, displayFragment).commit();
			displayFragment.onRefresh();
		} catch (InstantiationException e) {
			PanoptimageMsg.showErrorMsg(this, e);
		} catch (IllegalAccessException e) {
			PanoptimageMsg.showErrorMsg(this, e);
		}
	}

	/**
	 * Callback for no item selected
	 * 
	 * @param parent current activity
	 */
	public void onNothingSelected(AdapterView<?> parent) {
		// Another interface callback
		Log.d(PanoptesConstants.TAGNAME, "CreateActivity:onNothingSelected");
	}

	// -------------------------------------------------------------------------
	// Methods of interface RepositoryExistsListener
	// -------------------------------------------------------------------------
	@Override
	public void onExistsProgressUpdate(Integer... values) {
		// DO nothing
	}

	@Override
	public void onPostExists(Boolean result) {
		if (result) {
			PanoptimageMsg.showInfoMsg(this, getString(R.string.error_okserver));
		} else {
			PanoptimageMsg.showInfoMsg(this, getString(R.string.error_noserver));
		}
	}

	@Override
	public void onPreExists() {
		// do nothing
	}

	// -------------------------------------------------------------------------
	// private methods
	// -------------------------------------------------------------------------
	/**
	 * Finds the local param at a given position
	 * 
	 * @param pos position
	 * @return local param selected
	 */
	private LocalParam findLocalParamAt(int pos) {
		// locals are first
		if (pos < PanoptesTypeEnum.values().length || pos >= PanoptesTypeEnum.values().length + locals.size()) {
			return null;
		}
		return (LocalParam) locals.get(pos - PanoptesTypeEnum.values().length);
	}

	/**
	 * Finds the webdav param at a given position
	 * 
	 * @param pos position
	 * @return webdav param selected
	 */
	private WebdavParam findWebdavParamAt(int pos) {
		// webdav are next
		if (pos < PanoptesTypeEnum.values().length + locals.size()) {
			return null;
		}
		return (WebdavParam) webdavs.get(pos - PanoptesTypeEnum.values().length - locals.size());
	}

	/**
	 * Populate the spinner with the default strings and the list of already defined content
	 */
	private void populateSpinner() {
		// Identify the spinner
		Spinner selector = (Spinner) findViewById(R.id.create_selection);
		selector.setOnItemSelectedListener(this);
		// populate the spinner with default values
		List<Pair<CharSequence, PanoptesTypeEnum>> data = new ArrayList<Pair<CharSequence, PanoptesTypeEnum>>();
		for (PanoptesTypeEnum cur : PanoptesTypeEnum.values()) {
			data.add(new Pair<CharSequence, PanoptesTypeEnum>(getString(cur.key()), cur));
		}
		try {
			// extract configs
			locals = getHelper().getLocalParamDao().queryForAll();
			markLocalRead();
			webdavs = getHelper().getWebdavParamDao().queryForAll();
			markWebdavRead();

			// populate the spinner with local values
			for (CreateParam local : locals) {
				if (local.getKey() != null) {
					data.add(new Pair<CharSequence, PanoptesTypeEnum>(local.getKey(), PanoptesTypeEnum.LOCAL));
				}
			}
			// populate the spinner with webdav values
			for (CreateParam webdav : webdavs) {
				if (webdav.getKey() != null) {
					data.add(new Pair<CharSequence, PanoptesTypeEnum>(webdav.getKey(), PanoptesTypeEnum.WEBDAV));
				}
			}
		} catch (SQLException e) {
			PanoptimageMsg.showErrorMsg(this, e);
		}

		// populate the spinner with default values
		CreateParamsSpinnerAdapter adapter = new CreateParamsSpinnerAdapter(this, data, R.layout.round_spinner_item);
		adapter.setDropDownViewResource(R.layout.spinner_createparam);
		// Apply the adapter to the spinner
		selector.setAdapter(adapter);
	}
}
