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

package org.fereor.panoptimage.activity.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.activity.about.AboutActivity;
import org.fereor.panoptimage.activity.config.ConfigActivity;
import org.fereor.panoptimage.activity.create.CreateActivity;
import org.fereor.panoptimage.activity.image.ImageActivity;
import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.model.EmptyParam;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.util.PanoptimageConstants;
import org.fereor.panoptimage.util.PanoptimageMsg;
import org.fereor.panoptimage.util.PanoptimageTypeEnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends PanoptesActivity implements OnPageChangeListener {
	private static final String SAVESTATE_CURRENTITEM = "org.fereor.panoptimage.activity.home.HomeActivity.currentItem";

	/** Adapter for the pager */
	private HomePagerAdapter adapter;
	/** Pager for the List */
	private ViewPager pager;

	/** List of available configurations */
	private List<HomePagerParamService> content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// go on
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		try {
			adapter = new HomePagerAdapter(getSupportFragmentManager());
			// load content
			content = loadPagerContent();
			adapter.setData(content);
			pager = (ViewPager) findViewById(R.id.pager);
			pager.setAdapter(adapter);
			pager.setOnPageChangeListener(this);
			pager.setCurrentItem(findLastContent());
			// restore state if needed
			if (savedInstanceState != null) {
				pager.setCurrentItem(savedInstanceState.getInt(SAVESTATE_CURRENTITEM));
				adapter.notifyDataSetChanged();
			} else {
			}
		} catch (SQLException e) {
			PanoptimageMsg.showErrorMsg(this, e);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(SAVESTATE_CURRENTITEM, pager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		displayTutorials();
		// If database is updated, refresh content
		if (!isLocalParamUptodate() || !isWebdavParamUptodate()) {
			try {
				// load content
				content = loadPagerContent();
				// refresh adapter
				adapter.setData(content);
				pager.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				pager.setCurrentItem(findLastContent());
			} catch (SQLException e) {
				PanoptimageMsg.showErrorMsg(this, e);
			}
		}

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_create);
		showTooltip(R.id.tooltip_about);
		showTooltip(R.id.tooltip_back);
		showTooltip(R.id.tooltip_config);
		showTooltip(R.id.tooltip_slide);
	}

	@Override
	protected void displayTutorials() {
		if (tooltipvisible) {
			// tooltip is preminent on tutorial
			return;
		}
		try {
			long nbres = getHelper().getLocalParamDao().countOf() + getHelper().getWebdavParamDao().countOf();
			// Check if a config has been created
			if (nbres == 0) {
				showTutorial(R.id.tooltip_create);
			} else {
				showTutorial(R.id.tooltip_slide);
			}
		} catch (SQLException e) {
			// No tutorial
		}

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
		int location = pager.getCurrentItem();
		if (location >= content.size()) {
			PanoptimageMsg.showErrorMsg(this, getString(R.string.error_unknown));
			return;
		}

		// get data corresponding to item clicked
		HomePagerParamService curparam = content.get(location);
		if (!curparam.getParam().hasData()) {
			// This parameter has no data to display : return
			return;
		}
		// check network availability
		if (curparam.getParam().needNetwork() && !isNetworkAvailable()) {
			// Network is not available. Inform and return
			PanoptimageMsg.showErrorMsg(this, getString(R.string.error_nonetwork));
			return;
		}
		// Save param displayed
		try {
			Config data = getHelper().getConfigDao().queryForId(Config.DEFAULT_KEY);
			if (data != null) {
				data.setLastview(curparam.getParam().getKey());
				getHelper().getConfigDao().createOrUpdate(data);
			}
		} catch (SQLException e) {
		}
		// pass information to the image page
		Intent intent = new Intent(this, ImageActivity.class);
		intent.putExtra(PanoptimageConstants.MSG_HOME, curparam);
		startActivity(intent);
	}

	/** Called when the user clicks the Create button */
	public void doBack(View view) {
		finish();
	}

	/** Called when the user clicks the Create button */
	public void doCreate(View view) {
		Log.i(PanoptimageConstants.TAGNAME, "HomeActivity:showCreate");
		Intent intent = new Intent(this, CreateActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the Config button */
	public void doConfig(View view) {
		Log.i(PanoptimageConstants.TAGNAME, "HomeActivity:showConfig");
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the About button */
	public void doAbout(View view) {
		Log.i(PanoptimageConstants.TAGNAME, "HomeActivity:showAbout");
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	// -------------------------------------------------------------------------
	// Methods for PageChange Listener
	// -------------------------------------------------------------------------
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// nothing

	}

	@Override
	public void onPageScrolled(int idx, float arg1, int arg2) {
		// nothing
	}

	@Override
	public void onPageSelected(int idx) {
		if (tooltipvisible) {
			// tooltip is preminent on tutorial
			return;
		}
		if (idx != 0) {
			hideTutorial(R.id.tooltip_slide);
			showTutorial(R.id.tooltip_press);
		} else {
			showTutorial(R.id.tooltip_slide);
			hideTutorial(R.id.tooltip_press);
		}
	}

	// -------------------------------------------------------------------------
	// private Methods
	// -------------------------------------------------------------------------
	/**
	 * Fill the scroll view with the default strings and the list of already defined content
	 * 
	 * @throws SQLException
	 */
	private List<HomePagerParamService> loadPagerContent() throws SQLException {
		// populate the spinner with default values
		List<HomePagerParamService> data = new ArrayList<HomePagerParamService>();
		// Add the default view (home page)
		data.add(new HomePagerParamService(new EmptyParam(), getString(R.string.message), PanoptimageTypeEnum.EMPTY
				.icon()));

		// populate the scroll with local values
		for (LocalParam local : getHelper().getLocalParamDao().queryForAll()) {
			if (local.getKey() != null) {
				data.add(new HomePagerParamService(local, local.getKey(), PanoptimageTypeEnum.LOCAL.icon()));
			}
		}
		markLocalRead();
		// populate the scroll with webdav values
		for (WebdavParam webdav : getHelper().getWebdavParamDao().queryForAll()) {
			if (webdav.getKey() != null) {
				data.add(new HomePagerParamService(webdav, webdav.getKey(), PanoptimageTypeEnum.WEBDAV.icon()));
			}
		}
		markWebdavRead();
		return data;
	}

	/**
	 * Get the last content opened
	 */
	private int findLastContent() {
		// Read param displayed
		try {
			Config data = getHelper().getConfigDao().queryForId(Config.DEFAULT_KEY);
			if (data != null) {
				String loc = data.getLastview();
				if (loc == null) {
					return 0;
				}
				// Search a matching value
				int idx = 0;
				for (HomePagerParamService ct : content) {
					if (loc.equals(ct.getParam().getKey())) {
						return idx;
					}
					idx++;
				}
			}
		} catch (SQLException e) {
		}
		// default value : show default page
		return 0;
	}

}
