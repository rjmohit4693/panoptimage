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

package org.fereor.panoptimage.activity.image;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderFactory;
import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.service.async.RepositoryDirAsync;
import org.fereor.panoptimage.service.async.RepositoryDirListener;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ImageActivity extends PanoptesActivity implements OnItemClickListener,
		RepositoryDirListener<Long, List<String>> {
	private static final String SAVESTATE_CURRENTITEM = "org.fereor.panoptimage.activity.image.ImageActivity.currentItem";
	private static final String SAVESTATE_CURRENTPATH = "org.fereor.panoptimage.activity.image.ImageActivity.currentPath";
	private RepositoryLoaderDao<?> repoBrowser;
	private ImagePagerAdapter adapter;
	private ViewPager pager;
	private Bundle savedState;
	private Config config;
	private PanoptimageMemoryOptimEnum optim;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout
		setContentView(R.layout.activity_image);
		// Get the message from the intent
		Intent intent = getIntent();
		HomePagerParamService param = (HomePagerParamService) intent.getParcelableExtra(PanoptesConstants.MSG_HOME);
		// hide panel
		hideBrowserPanel();
		try {
			// Retrieve content
			repoBrowser = RepositoryLoaderFactory.createInstance(param, new File(getFilesDir(), PanoptesConstants.CACHE_DIR));
			// check network availability
			if (param.getParam().needNetwork() && !isNetworkAvailable()) {
				// Network is not available. Inform and return
				showErrorMsg(getString(R.string.error_nonetwork));
				return;
			}
			// set temporary adapter
			adapter = new LoadingPagerAdapter(getSupportFragmentManager(), optim);
			pager = (ViewPager) findViewById(R.id.imagepager);
			pager.setAdapter(adapter);
			pager.setOffscreenPageLimit(1);
			savedState = savedInstanceState;
			// restore state if needed
			if (savedState != null) {
				repoBrowser.cd(savedState.getString(SAVESTATE_CURRENTPATH));
			}
			// Launch loading task
			RepositoryDirAsync task = new RepositoryDirAsync(this, PanoptesHelper.REGEXP_ALLIMAGES);
			task.execute(repoBrowser);
		} catch (PanoptimageFileNotFoundException e) {
			showErrorMsg(R.string.error_filenotfound, e.getLocation());
		} catch (PanoptesUnknownParamException e) {
			showErrorMsg(getString(R.string.error_unknown_param));
		} catch (PanoptimageNoNetworkException e) {
			showErrorMsg(getString(R.string.error_nonetwork));
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(PanoptesConstants.TAGNAME, "onSaveInstanceState");
		outState.putString(SAVESTATE_CURRENTPATH, repoBrowser.getformatedPath());
		outState.putInt(SAVESTATE_CURRENTITEM, pager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		super.onResume();
		loadConfig();
		// hide panel
		// hideBrowserPanel();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_image, menu);
		return true;
	}
	
	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_browse);  
		showTooltip(R.id.tooltip_back);  
		showTooltip(R.id.tooltip_rotate_clockwise);  
		showTooltip(R.id.tooltip_rotate_counterclockwise);		
	}

	// -------------------------------------------------------------------------
	// Methods for Dir listener
	// -------------------------------------------------------------------------

	@Override
	public void onPreDir() {
		// do nothing
	}

	@Override
	public void onPostDir(List<String> result) {
		// set adapter
		adapter = new ImagePagerAdapter(repoBrowser, getSupportFragmentManager(), optim);
		adapter.setImageList(result);
		pager = (ViewPager) findViewById(R.id.imagepager);
		pager.setAdapter(adapter);
		if (savedState != null) {
			pager.setCurrentItem(savedState.getInt(SAVESTATE_CURRENTITEM));
			savedState = null;
		}
		pager.setOffscreenPageLimit(1);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onDirProgressUpdate(Long... values) {
		// do nothing
	}

	@Override
	public void onOEM(Throwable t) {
		showErrorMsg(R.string.error_outofmemory);
	}
	
	// -------------------------------------------------------------------------
	// Methods for Action buttons
	// -------------------------------------------------------------------------
	/**
	 * Called when the image is clicked
	 * 
	 * @param view image clicked
	 * @return
	 */
	public void onImageClicked(View view) {
		// hide panel
		hideBrowserPanel();
		// TODO : go to the zoomed image
	}

	/**
	 * Back button clicked
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		finish();
	}

	/**
	 * Browse button clicked
	 * 
	 * @param view
	 */
	public void doBrowse(View view) {
		if (repoBrowser == null) {
			return;
		}
		// start async task to load content
		ImageBrowserFragment browseFragment = (ImageBrowserFragment) getSupportFragmentManager().findFragmentById(
				R.id.browser_fragment);
		browseFragment.setRoot(repoBrowser.isRoot());
		RepositoryDirAsync task = new RepositoryDirAsync(browseFragment, PanoptesHelper.REGEXP_DIRECTORY);
		task.execute(repoBrowser);
		showBrowserPanel();
	}

	/**
	 * hide button clicked
	 * 
	 * @param view
	 */
	public void doHidePanel(View view) {
		// hide panel
		hideBrowserPanel();
	}

	@Override
	public void onItemClick(AdapterView<?> lView, View v, int position, long id) {
		// hide panel
		hideBrowserPanel();
		Object item = lView.getAdapter().getItem(position);

		// set temporary adapter
		try {
			adapter = new LoadingPagerAdapter(getSupportFragmentManager(), optim);
		} catch (PanoptimageFileNotFoundException e) {
			// cannot happen on LoadingPagerAdapter
		}
		pager = (ViewPager) findViewById(R.id.imagepager);
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(0);
		Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show();
		// change to directory selected
		repoBrowser.cd(item.toString());
		RepositoryDirAsync task = new RepositoryDirAsync(this, PanoptesHelper.REGEXP_ALLIMAGES);
		task.execute(repoBrowser);
	}

	/**
	 * Rotate clockwise button clicked
	 * 
	 * @param view
	 */
	public void doRotateClockwise(View view) {
		// get current fragment displayed
		if (adapter != null) {
			ImageListFragment fmt = adapter.getCurrentItem();
			if (fmt != null)
				fmt.rotateClockwise();
		}
	}

	/**
	 * Rotate counterclockwise button clicked
	 * 
	 * @param view
	 */
	public void doRotateCounterClockwise(View view) {
		// get current fragment displayed
		if (adapter != null) {
			ImageListFragment fmt = adapter.getCurrentItem();
			if (fmt != null)
				fmt.rotateCounterClockwise();
		}
	}

	/**
	 * Hide the browser fragment
	 */
	private void hideBrowserPanel() {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.browser_fragment);
		getSupportFragmentManager().beginTransaction().hide(browseFragment).commit();
	}

	/**
	 * Show the browser fragment
	 */
	private void showBrowserPanel() {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.browser_fragment);
		getSupportFragmentManager().beginTransaction().show(browseFragment).commit();
	}

	/**
	 * Load the config data
	 * 
	 * @throws SQLException
	 */
	private void loadConfig() {
		if (!isConfigUptodate()) {
			try {
				config = getHelper().getConfigDao().queryForId(Config.DEFAULT_KEY);
				optim = PanoptimageMemoryOptimEnum.values()[PanoptimageMemoryOptimEnum.findPosition(config
						.getMemoptim())];
			} catch (Exception e) {
				config = null;
				optim = PanoptimageMemoryOptimEnum.Auto;
			}
		}
	}
}
